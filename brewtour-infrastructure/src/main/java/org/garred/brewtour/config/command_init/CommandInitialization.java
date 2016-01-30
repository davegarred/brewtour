package org.garred.brewtour.config.command_init;

import static java.util.Collections.emptyList;
import static org.garred.brewtour.domain.AvailableImages.NO_IMAGES;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.garred.brewtour.application.IdentifierFactory;
import org.garred.brewtour.application.command.location.AbstractLocationCommand;
import org.garred.brewtour.application.command.location.AddBeerCommand;
import org.garred.brewtour.application.command.location.AddLocationCommand;
import org.garred.brewtour.application.command.location.UpdateLocationAddressCommand;
import org.garred.brewtour.application.command.location.UpdateLocationDescriptionCommand;
import org.garred.brewtour.application.command.location.UpdateLocationHoursOfOperationCommand;
import org.garred.brewtour.application.command.location.UpdateLocationImagesCommand;
import org.garred.brewtour.application.command.location.UpdateLocationPhoneCommand;
import org.garred.brewtour.application.command.location.UpdateLocationPositionCommand;
import org.garred.brewtour.application.command.location.UpdateLocationWebsiteCommand;
import org.garred.brewtour.application.command.user.AddUserCommand;
import org.garred.brewtour.brewdb.BrewDbBeer;
import org.garred.brewtour.brewdb.BrewDbBeer.BrewDbBeerList;
import org.garred.brewtour.brewdb.BrewDbBeer.Style;
import org.garred.brewtour.brewdb.BrewDbLocation;
import org.garred.brewtour.brewdb.BrewDbLocation.BrewDbLocationList;
import org.garred.brewtour.domain.AvailableImages;
import org.garred.brewtour.domain.Beer;
import org.garred.brewtour.domain.Image;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.infrastructure.ObjectMapperFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommandInitialization implements ApplicationListener<ContextRefreshedEvent> {

	private final CommandGateway gateway;
	private final IdentifierFactory<LocationId> identifierFactory;

	public CommandInitialization(CommandGateway gateway, IdentifierFactory<LocationId> identifierFactory) {
		this.gateway = gateway;
		this.identifierFactory = identifierFactory;
	}


	private final List<UpdateLocationAddressCommand> addressUpdates = new ArrayList<>();
	private final List<UpdateLocationDescriptionCommand> descriptionUpdates = new ArrayList<>();
	private final List<UpdateLocationHoursOfOperationCommand> hoursOfOperationUpdates = new ArrayList<>();
	private final List<UpdateLocationImagesCommand> imagesUpdates = new ArrayList<>();
	private final List<UpdateLocationPhoneCommand> phoneUpdates = new ArrayList<>();
	private final List<UpdateLocationPositionCommand> positionUpdates = new ArrayList<>();
	private final List<UpdateLocationWebsiteCommand> websiteUpdates = new ArrayList<>();

	private final Map<LocationId,Map<String,AddBeerCommand>> addBeerCommands = new HashMap<>();

	private final Map<String, Beer> beerMap = new HashMap<>();

	public void migrate() throws Exception {
		final ObjectMapper objectMapper = ObjectMapperFactory.objectMapper();
		loadBeerData(objectMapper);
		final InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("json/test_locations.json");
		final BrewDbLocationList locations = objectMapper.readValue(in, BrewDbLocationList.class);
		for (final BrewDbLocation brewDbLocation : locations) {
			populateLists(brewDbLocation);
		}
		push(this.addressUpdates);
		push(this.descriptionUpdates);
		push(this.hoursOfOperationUpdates);
		push(this.imagesUpdates);
		push(this.phoneUpdates);
		push(this.positionUpdates);
		push(this.websiteUpdates);
		push(this.addBeerCommands);
	}

	private void push(Map<LocationId,Map<String, AddBeerCommand>> commands) {
		for(final Entry<LocationId, Map<String, AddBeerCommand>> entry : commands.entrySet()) {
			push(entry.getValue().values());
		}
	}

	private void push(Collection<? extends AbstractLocationCommand> commands) {
		try {
			commands.stream().forEach(c -> this.gateway.send(c));
		} catch(final Exception e) {
			e.printStackTrace();
		}
	}

	private void populateLists(BrewDbLocation brewDbLocation) {
		this.gateway.sendAndWait(new AddLocationCommand(brewDbLocation.id, brewDbLocation.brewery.name));
		final LocationId locationId = this.identifierFactory.last();

		this.addressUpdates.add(new UpdateLocationAddressCommand(locationId, brewDbLocation.streetAddress, "",
				brewDbLocation.locality, brewDbLocation.region, brewDbLocation.postalCode));
		this.descriptionUpdates.add(new UpdateLocationDescriptionCommand(locationId, brewDbLocation.brewery.description));
		this.hoursOfOperationUpdates.add(new UpdateLocationHoursOfOperationCommand(locationId, brewDbLocation.hoursOfOperation));


		final Map<String, String> bdbImages = brewDbLocation.brewery.images;
		final AvailableImages images = bdbImages == null ? NO_IMAGES
				: new AvailableImages(new Image(bdbImages.get("icon")), new Image(bdbImages.get("medium")),
						new Image(bdbImages.get("large")));
		this.imagesUpdates.add(new UpdateLocationImagesCommand(locationId, images));

		this.phoneUpdates.add(new UpdateLocationPhoneCommand(locationId, brewDbLocation.phone));
		this.positionUpdates.add(new UpdateLocationPositionCommand(locationId, brewDbLocation.latitude, brewDbLocation.longitude));
		this.websiteUpdates.add(new UpdateLocationWebsiteCommand(locationId, brewDbLocation.website));


		final HashMap<String, AddBeerCommand> locationBeerMap = new HashMap<>();
		this.addBeerCommands.put(locationId, locationBeerMap);
		if (brewDbLocation.beerIds != null) {
			for (final String beerId : brewDbLocation.beerIds) {
				final Beer beer = this.beerMap.get(beerId);
				final String beerName = beer.getName();
				locationBeerMap.put(beerName.trim().toLowerCase(), new AddBeerCommand(locationId, beerName, beer.getStyle(), beer.getCategory(), beer.getAbv(), beer.getIbu()));
			}
		}
	}

	private void loadBeerData(ObjectMapper objectMapper) throws JsonParseException, JsonMappingException, IOException {
		final Set<Style> bdbStyles = new HashSet<>();
		try (final InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("json/test_beers.json")) {
			final BrewDbBeerList beers = objectMapper.readValue(in, BrewDbBeerList.class);
			for (final BrewDbBeer brewDbBeer : beers) {
				final Beer beer = convert(brewDbBeer);
				bdbStyles.add(brewDbBeer.style);
				this.beerMap.put(brewDbBeer.id, beer);
			}
		}
	}

	private static Beer convert(BrewDbBeer beer) {
		return new Beer(beer.id, beer.name, beer.status, beer.style == null ? "" : beer.style.name,
				beer.style == null ? "" : beer.style.category == null ? "" : beer.style.category.name,
				beer.abv, beer.ibu, true, emptyList());
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		this.gateway.send(new AddUserCommand(new UserId(UUID.randomUUID().toString()), "dave", "dave"));
		try {
			migrate();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
}
