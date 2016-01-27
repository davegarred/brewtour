package org.garred.brewtour.config.command_init;

import static java.util.Collections.emptyList;
import static org.garred.brewtour.application.AvailableImages.NO_IMAGES;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.garred.brewtour.api.AddPopulatedLocationCommand;
import org.garred.brewtour.application.AvailableImages;
import org.garred.brewtour.application.Beer;
import org.garred.brewtour.application.Image;
import org.garred.brewtour.brewdb.BrewDbBeer;
import org.garred.brewtour.brewdb.BrewDbBeer.BrewDbBeerList;
import org.garred.brewtour.brewdb.BrewDbBeer.Style;
import org.garred.brewtour.brewdb.BrewDbLocation;
import org.garred.brewtour.brewdb.BrewDbLocation.BrewDbLocationList;
import org.garred.brewtour.infrastructure.ObjectMapperFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommandInitialization implements ApplicationListener<ContextRefreshedEvent> {

	private final CommandGateway gateway;

	public CommandInitialization(CommandGateway gateway) {
		this.gateway = gateway;
	}

	private static Map<String, Beer> beerMap = new HashMap<>();

	public void migrate() throws Exception {
		final ObjectMapper objectMapper = ObjectMapperFactory.objectMapper();
		loadBeerData(objectMapper);
		final InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("json/test_locations.json");
		final BrewDbLocationList locations = objectMapper.readValue(in, BrewDbLocationList.class);
		for (final BrewDbLocation brewDbLocation : locations) {
			final AddPopulatedLocationCommand command = convertToLocation(brewDbLocation);
			this.gateway.send(command);
		}
	}

	private static AddPopulatedLocationCommand convertToLocation(BrewDbLocation brewDbLocation) {
		final Map<String, String> bdbImages = brewDbLocation.brewery.images;
		final AvailableImages images = bdbImages == null ? NO_IMAGES
				: new AvailableImages(new Image(bdbImages.get("icon")), new Image(bdbImages.get("medium")),
						new Image(bdbImages.get("large")));
		final List<Beer> beers = new ArrayList<>();
		if (brewDbLocation.beerIds != null) {
			for (final String beerId : brewDbLocation.beerIds) {
				beers.add(beerMap.get(beerId));
			}
		}
		return new AddPopulatedLocationCommand(brewDbLocation.id, brewDbLocation.brewery.name, brewDbLocation.brewery.description, brewDbLocation.latitude,
				brewDbLocation.longitude, images, beers);
	}

	private static void loadBeerData(ObjectMapper objectMapper) throws JsonParseException, JsonMappingException, IOException {
		final Set<Style> bdbStyles = new HashSet<>();
		try (final InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("json/test_beers.json")) {
			final BrewDbBeerList beers = objectMapper.readValue(in, BrewDbBeerList.class);
			for (final BrewDbBeer brewDbBeer : beers) {
				final Beer beer = convert(brewDbBeer);
				bdbStyles.add(brewDbBeer.style);
				beerMap.put(brewDbBeer.id, beer);
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
		try {
			migrate();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
}
