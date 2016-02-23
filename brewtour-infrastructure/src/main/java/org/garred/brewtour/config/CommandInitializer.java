package org.garred.brewtour.config;

import static java.util.stream.Collectors.toList;
import static org.garred.brewtour.application.command.GenericAddAggregateCallback.forCommand;
import static org.garred.brewtour.config.BrewDbDataPrep.BEER_FILE;
import static org.garred.brewtour.config.BrewDbDataPrep.BREWERY_FILE;
import static org.garred.brewtour.config.BrewDbDataPrep.LOCATION_FILE;
import static org.garred.brewtour.security.SystemUserAuth.SYSTEM;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.garred.brewdb.domain.Beer;
import org.garred.brewdb.domain.Images;
import org.garred.brewdb.domain.Location;
import org.garred.brewtour.application.command.GenericAddAggregateCallback;
import org.garred.brewtour.application.command.beer.AddBeerCommand;
import org.garred.brewtour.application.command.beer.UpdateBeerImagesCommand;
import org.garred.brewtour.application.command.brewery.AddBreweryCommand;
import org.garred.brewtour.application.command.location.AbstractLocationCommand;
import org.garred.brewtour.application.command.location.AddLocationCommand;
import org.garred.brewtour.application.command.location.BeerAvailableCommand;
import org.garred.brewtour.application.command.location.UpdateLocationAddressCommand;
import org.garred.brewtour.application.command.location.UpdateLocationDescriptionCommand;
import org.garred.brewtour.application.command.location.UpdateLocationHoursOfOperationCommand;
import org.garred.brewtour.application.command.location.UpdateLocationImagesCommand;
import org.garred.brewtour.application.command.location.UpdateLocationPhoneCommand;
import org.garred.brewtour.application.command.location.UpdateLocationPositionCommand;
import org.garred.brewtour.application.command.location.UpdateLocationWebsiteCommand;
import org.garred.brewtour.application.command.user.AddUserCommand;
import org.garred.brewtour.domain.AvailableImages;
import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.BreweryId;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.infrastructure.ObjectMapperFactory;
import org.garred.brewtour.security.UserHolder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CommandInitializer implements ApplicationListener<ContextRefreshedEvent> {

		private static volatile boolean completed = false;

		private final ObjectMapper objectMapper = ObjectMapperFactory.objectMapper();

		public final Map<String,Location> locationMap;
		public final Map<String,BreweryData> breweryMap;
		public final Map<String,BeerSetMapping> beerMap;

		public final Map<String,LocationId> nameToLocationIdMap = new HashMap<>();
		public final Map<String,BreweryId> nameToBreweryIdMap = new HashMap<>();

		private final CommandGateway commandGateway;

		public CommandInitializer(CommandGateway commandGateway) {
			this.commandGateway = commandGateway;
			try(
					final InputStream locationFile = Thread.currentThread().getContextClassLoader().getResourceAsStream(LOCATION_FILE);
					final InputStream breweryFile = Thread.currentThread().getContextClassLoader().getResourceAsStream(BREWERY_FILE);
					final InputStream beerFile = Thread.currentThread().getContextClassLoader().getResourceAsStream(BEER_FILE);
					){
				this.locationMap = this.objectMapper.readValue(locationFile, LocationMapping.class);
				this.breweryMap = this.objectMapper.readValue(breweryFile, BreweryMapping.class);
				this.beerMap = this.objectMapper.readValue(beerFile, BeerMapping.class);
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
		}
		@SuppressWarnings("serial")
		public static class LocationMapping extends HashMap<String,Location> {}
		@SuppressWarnings("serial")
		public static class BreweryMapping extends HashMap<String,BreweryData> {}
		@SuppressWarnings("serial")
		public static class BeerMapping extends HashMap<String,BeerSetMapping> {}
		@SuppressWarnings("serial")
		public static class BeerSetMapping extends HashSet<Beer> {}
		@SuppressWarnings("serial")
		public static class CustomLocationCommandList extends ArrayList<CustomLocationCommand> {}
		@SuppressWarnings("serial")
		public static class CustomBeerCommandList extends ArrayList<CustomAddBeerCommand> {}


		private LocationId fireLocationCommands(Location brewDbLocation) {
			final AddLocationCommand command = new AddLocationCommand(brewDbLocation.brewery.name);
			final GenericAddAggregateCallback<LocationId> callback = GenericAddAggregateCallback.forCommand(command);
			this.commandGateway.sendAndWait(command);
			final LocationId locationId = callback.identifier();
			this.nameToLocationIdMap.put(brewDbLocation.brewery.name, locationId);

			this.commandGateway.sendAndWait(new UpdateLocationAddressCommand(locationId, brewDbLocation.streetAddress, "",
					brewDbLocation.locality, brewDbLocation.region, brewDbLocation.postalCode));

			if(brewDbLocation.brewery.description != null) {
				this.commandGateway.sendAndWait(new UpdateLocationDescriptionCommand(locationId, brewDbLocation.brewery.description));
			}

			if(brewDbLocation.hoursOfOperation != null) {
				this.commandGateway.sendAndWait(new UpdateLocationHoursOfOperationCommand(locationId, brewDbLocation.hoursOfOperation));
			}


			final Images bdbImages = brewDbLocation.brewery.images;
			if(bdbImages != null && (
					bdbImages.icon != null ||
					bdbImages.medium != null ||
					bdbImages.large != null)) {
				this.commandGateway.sendAndWait(new UpdateLocationImagesCommand(locationId, AvailableImages.fromString(bdbImages.icon, bdbImages.medium,bdbImages.large)));
			}


			if(brewDbLocation.phone != null) {
				this.commandGateway.sendAndWait(new UpdateLocationPhoneCommand(locationId, brewDbLocation.phone));
			}

			if(brewDbLocation.latitude != null && brewDbLocation.longitude != null) {
				this.commandGateway.sendAndWait(new UpdateLocationPositionCommand(locationId, brewDbLocation.latitude, brewDbLocation.longitude));
			}

			if(brewDbLocation.website != null) {
				this.commandGateway.sendAndWait(new UpdateLocationWebsiteCommand(locationId, brewDbLocation.website));
			}

			return locationId;
		}

		private BreweryId fireBreweryCommands(BreweryData brewery) {
			final AddBreweryCommand command = new AddBreweryCommand(brewery.breweryName);
			final GenericAddAggregateCallback<BreweryId> callback = forCommand(command);
			this.commandGateway.sendAndWait(command);
			final BreweryId breweryId = callback.identifier();
			this.nameToBreweryIdMap.put(brewery.breweryName, breweryId);
			return breweryId;
		}
		private BeerId fireBeerCommands(Beer beer, BreweryId breweryId, String breweryName, List<LocationId> locationIds) {
			final AddBeerCommand command = new AddBeerCommand(beer.name, beer.description, breweryId, breweryName,
					beer.style == null ? null : beer.style.name,
					(beer.style == null || beer.style.category == null) ? null : beer.style.category.name,
					beer.abv == null ? null : new BigDecimal(beer.abv),
					beer.ibu == null ? null : new BigDecimal(beer.ibu));
			final GenericAddAggregateCallback<BeerId> callback = forCommand(command);
			this.commandGateway.sendAndWait(command);
			final BeerId beerId = callback.identifier();

			final Images bdbImages = beer.labels;
			if(bdbImages != null && (
					bdbImages.icon != null ||
					bdbImages.medium != null ||
					bdbImages.large != null)) {
				this.commandGateway.sendAndWait(new UpdateBeerImagesCommand(beerId, AvailableImages.fromString(bdbImages.icon, bdbImages.medium,bdbImages.large)));
			}

			for(final LocationId locationId : locationIds) {
				this.commandGateway.sendAndWait(new BeerAvailableCommand(locationId, beerId));
			}
			return beerId;
		}

		@Override
		public synchronized void onApplicationEvent(ContextRefreshedEvent event) {
			if(completed) {
				return;
			}
			completed = true;
			UserHolder.set(SYSTEM);
			this.commandGateway.send(new AddUserCommand("FBS", "dave", "password"));
			final Map<String,LocationId> brewDbLocationIdMap = new HashMap<>();
			for(final Location location : this.locationMap.values()) {
				final LocationId locationId = fireLocationCommands(location);
				brewDbLocationIdMap.put(location.id, locationId);
			}
			for(final BreweryData brewery : this.breweryMap.values()) {
				final BreweryId breweryId = fireBreweryCommands(brewery);
				final String breweryName = brewery.breweryName;
				final List<LocationId> locationIds = brewery.locationIds.stream()
						.map(l -> brewDbLocationIdMap.get(l))
						.collect(toList());
				final BeerSetMapping beers = this.beerMap.get(brewery.breweryId);
				if(beers != null) {
					for(final Beer beer : beers) {
						fireBeerCommands(beer, breweryId, breweryName, locationIds);
					}
				}
			}
			this.fireCustomCommands();
		}



		private void fireCustomCommands() {
			for(final NewLocationCommands commandList : customNewLocationCommands()) {
				final AddLocationCommand addCommand = commandList.addCommand;
				final GenericAddAggregateCallback<LocationId> callback = GenericAddAggregateCallback.forCommand(addCommand);
				this.commandGateway.sendAndWait(addCommand);
				final LocationId locationId = callback.identifier();

				this.nameToLocationIdMap.put(addCommand.name, locationId);
				final AddBreweryCommand addBreweryCommand = new AddBreweryCommand(addCommand.name);
				final GenericAddAggregateCallback<BreweryId> breweryCallback = GenericAddAggregateCallback.forCommand(addBreweryCommand);
				this.commandGateway.sendAndWait(addBreweryCommand);
				final BreweryId breweryId = breweryCallback.identifier();
				this.nameToBreweryIdMap.put(addBreweryCommand.breweryName, breweryId);

				for(final AbstractLocationCommand command : commandList.detailCommands) {
					this.commandGateway.sendAndWait(setLocation(locationId, command));
				}
			}
			for(final ExistingLocationCommands command : buildCustomExistingLocationCommands()) {
				final LocationId locationId = this.nameToLocationIdMap.get(command.locationName);
				this.commandGateway.sendAndWait(setLocation(locationId, command.command));
			}
			for(final AddBeerCommand command : customAddBeerCommands()) {
				final LocationId locationId = this.nameToLocationIdMap.get(command.breweryName);
				final GenericAddAggregateCallback<BeerId> callback = GenericAddAggregateCallback.forCommand(command);
				final BreweryId breweryId = this.nameToBreweryIdMap.get(command.breweryName);
				this.commandGateway.sendAndWait(setField(breweryId, "breweryId", command));
				this.commandGateway.sendAndWait(new BeerAvailableCommand(locationId, callback.identifier()));
			}
		}

		private static Object setLocation(final LocationId locationId, final AbstractLocationCommand command) {
			return setField(locationId, "locationId", command);
		}
		private static Object setField(final Object object, final String fieldName, final Object command) {
			try {
				final Field field = command.getClass().getField(fieldName);
				field.setAccessible(true);
				field.set(command, object);
				return command;
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}

		private static Collection<AddBeerCommand> customAddBeerCommands() {
			final List<AddBeerCommand> result = new ArrayList<>();
			result.add(new AddBeerCommand("Boss Fight Triple IPA", "Triple IPA Season is upon us! Intense hop flavors and aromas from heavy double dry hopping at over 2 pounds per barrel. Our 3x IPA is bigger, meaner, and has a lot more hit points than your standard IPA.", null, "Lucky Envelope Brewing", null, "Imperial or Double India Pale Ale", new BigDecimal("10.3"), new BigDecimal("104")));
			result.add(new AddBeerCommand("ENIAC 2.0 Mosaic India Pale Ale", "Version 2.0 of our ENIAC IPA screams Mosaic hops and a balanced malt profile with notes of juicy tropical fruit and resinous pine.", null, "Lucky Envelope Brewing", null, "American-Style India Pale Ale", new BigDecimal("6.4"), new BigDecimal("68")));
			result.add(new AddBeerCommand("50th Street India Pale Ale", "Our San Diego-inspired IPA is a bitter and grapefruit-forward hoppy beer. The pilsner malt base is crisp and clean to let the hop profile shine.", null, "Lucky Envelope Brewing", null, "American-Style India Pale Ale", new BigDecimal("6.8"), new BigDecimal("80")));
			result.add(new AddBeerCommand("Galaxy Session IPA", "This hoppy, yet sessionable beer packs the hop kick of a traditional IPA but with less alcohol. Australian Galaxy hops provide a juicy, tropical fruit aroma.", null, "Lucky Envelope Brewing", null, "Session India Pale Ale", new BigDecimal("4.6"), new BigDecimal("52")));
			result.add(new AddBeerCommand("Imperial Porter", "The imperial robust porter packs complex malt flavor while still being able to enjoy a pint. This beer is bold and chewy with flavors of espresso, milk chocolate, and caramel.", null, "Lucky Envelope Brewing", null, "American-Style Imperial Porter", new BigDecimal("7.5"), new BigDecimal("42")));
			result.add(new AddBeerCommand("Flying Envelope Washington Lager", "This American Craft Lager uses 100% Washington-grown ingredients and our house lager strain. Light and crisp, this beer showcases local heirloom malt with a smooth bready finish.", null, "Lucky Envelope Brewing", null, null, new BigDecimal("4.7"), new BigDecimal("26")));
			result.add(new AddBeerCommand("Buddha’s Hand Pale Ale", "A classic Pacific Northwest pale ale infused with zest from Buddha’s Hand and Chinese pomelo citrus fruits. Both citrus fruits are native to southeastern Asia", null, "Lucky Envelope Brewing", null, "American-Style Pale Ale", new BigDecimal("5.1"), new BigDecimal("38")));


			result.add(new AddBeerCommand("Pigeonhole IPA", "IPA with Chinook, Citra, Amarillo, and LOTS of Simcoe", null, "Cloudburst Brewing", null, "American-Style India Pale Ale", null, null));
			result.add(new AddBeerCommand("Born Again", "Strong Blonde Ale spiced with grains of paradise, long peppercorns, & coriander", null, "Cloudburst Brewing", null, null, null, null));
			result.add(new AddBeerCommand("Punch Drunk Love", "Strong Pale Ale with Sweet Cherry puree and Pineapple juice.", null, "Cloudburst Brewing", null, null, null, null));
			result.add(new AddBeerCommand("Psycho Hose Beast", "Triple IPA", null, "Cloudburst Brewing", null, null, null, null));
			result.add(new AddBeerCommand("Market Fresh Saison", "Version 2: Grapefruit & Rosemary", null, "Cloudburst Brewing", null, null, null, null));
			result.add(new AddBeerCommand("Aw Shucks", "Oyster Stout", null, "Cloudburst Brewing", null, null, null, null));
			result.add(new AddBeerCommand("Unreliable Narrator", "Dry Hopped Pale with GR Mandarina & NZ Nelson Sauvin hops", null, "Cloudburst Brewing", null, null, null, null));
			result.add(new AddBeerCommand("Cure-All", "Nitro Milk Stout", null, "Cloudburst Brewing", null, null, null, null));
			result.add(new AddBeerCommand("Hotline Bling IPA", "IPA with Mosaic, Galaxy, & El Dorado", null, "Cloudburst Brewing", null, null, null, null));
			result.add(new AddBeerCommand("Hoppy Little Clouds", "Dry-Hopped Pilsner", null, "Cloudburst Brewing", null, null, null, null));
			return result;
		}

		private static Collection<NewLocationCommands> customNewLocationCommands() {
			final NewLocationCommands cloudburst = new NewLocationCommands(new AddLocationCommand("Cloudburst Brewing"),
					new UpdateLocationAddressCommand(null, "2116 Western Ave", null, "Seattle", "WA", "98121"),
					new UpdateLocationPositionCommand(null, new BigDecimal("47.6112849"), new BigDecimal("-122.345076")),
					new UpdateLocationWebsiteCommand(null, "http://cloudburstbrew.com/"),
					new UpdateLocationImagesCommand(null, AvailableImages.fromString(null, "resources/img/breweries/initial/cloudburst.png", null))
					);
			return Collections.singletonList(cloudburst);
		}
		private static Collection<ExistingLocationCommands> buildCustomExistingLocationCommands() {
			final List<ExistingLocationCommands> commands = new ArrayList<>();
			commands.add(new ExistingLocationCommands("Lucky Envelope Brewing",new UpdateLocationWebsiteCommand(null, "http://www.luckyenvelopebrewing.com/")));
			commands.add(new ExistingLocationCommands("Populuxe Brewing",new UpdateLocationWebsiteCommand(null, "https://www.facebook.com/PopuluxeBrewing/")));
			commands.add(new ExistingLocationCommands("NW Peaks Brewery",new UpdateLocationWebsiteCommand(null, "http://www.nwpeaksbrewery.com/")));
			return commands;
		}
		private static class NewLocationCommands {
			public final AddLocationCommand addCommand;
			public final List<AbstractLocationCommand> detailCommands;
			public NewLocationCommands(AddLocationCommand addCommand, AbstractLocationCommand... detailCommands) {
				this.addCommand = addCommand;
				this.detailCommands = Arrays.asList(detailCommands);
			}

		}
		private static class ExistingLocationCommands {
			public final String locationName;
			public final AbstractLocationCommand command;
			public ExistingLocationCommands(String locationName, AbstractLocationCommand command) {
				this.locationName = locationName;
				this.command = command;
			}
		}

	}
