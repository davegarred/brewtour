package org.garred.brewtour.config;

import static java.math.RoundingMode.HALF_UP;
import static org.garred.brewtour.application.command.GenericAddAggregateCallback.forCommand;
import static org.garred.brewtour.config.BrewDbDataPrep.BEER_FILE;
import static org.garred.brewtour.config.BrewDbDataPrep.BREWERY_FILE;
import static org.garred.brewtour.config.BrewDbDataPrep.LOCATION_FILE;
import static org.garred.brewtour.security.SystemUserAuth.SYSTEM;
import static org.garred.brewtour.view.UserAuthView.ADMIN_ROLE;

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
import java.util.Map.Entry;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.garred.brewdb.domain.Beer;
import org.garred.brewdb.domain.Images;
import org.garred.brewdb.domain.Location;
import org.garred.brewtour.application.command.GenericAddAggregateCallback;
import org.garred.brewtour.application.command.beer.AbstractBeerCommand;
import org.garred.brewtour.application.command.beer.AddBeerCommand;
import org.garred.brewtour.application.command.beer.UpdateBeerAbvCommand;
import org.garred.brewtour.application.command.beer.UpdateBeerDescriptionCommand;
import org.garred.brewtour.application.command.beer.UpdateBeerIbuCommand;
import org.garred.brewtour.application.command.beer.UpdateBeerImagesCommand;
import org.garred.brewtour.application.command.beer.UpdateBeerStyleCommand;
import org.garred.brewtour.application.command.location.AbstractLocationCommand;
import org.garred.brewtour.application.command.location.AddLocationCommand;
import org.garred.brewtour.application.command.location.BeerAvailableCommand;
import org.garred.brewtour.application.command.location.UpdateLocationAddressCommand;
import org.garred.brewtour.application.command.location.UpdateLocationBreweryAssociationCommand;
import org.garred.brewtour.application.command.location.UpdateLocationDescriptionCommand;
import org.garred.brewtour.application.command.location.UpdateLocationHoursOfOperationCommand;
import org.garred.brewtour.application.command.location.UpdateLocationImagesCommand;
import org.garred.brewtour.application.command.location.UpdateLocationPhoneCommand;
import org.garred.brewtour.application.command.location.UpdateLocationPositionCommand;
import org.garred.brewtour.application.command.location.UpdateLocationWebsiteCommand;
import org.garred.brewtour.application.command.user.AddRoleToUserCommand;
import org.garred.brewtour.application.command.user.AddUserCommand;
import org.garred.brewtour.domain.AvailableImages;
import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.UserId;
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
		public final Map<String,LocationId> nameToBreweryIdMap = new HashMap<>();

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

		private BeerId fireBeerCommands(Beer beer, LocationId breweryId, String breweryName) {
			final AddBeerCommand command = new AddBeerCommand(beer.name, breweryId, breweryName);
			final GenericAddAggregateCallback<BeerId> callback = forCommand(command);
			this.commandGateway.sendAndWait(command);
			final BeerId beerId = callback.identifier();

			if(beer.description != null && !beer.description.isEmpty()) {
				this.commandGateway.sendAndWait(new UpdateBeerDescriptionCommand(beerId, beer.description));
			}
			if(beer.style != null) {
				this.commandGateway.sendAndWait(new UpdateBeerStyleCommand(beerId, beer.style.name));
			}
			if(beer.abv  != null && !beer.abv.isEmpty()) {
				this.commandGateway.sendAndWait(new UpdateBeerAbvCommand(beerId, new BigDecimal(beer.abv).setScale(1, HALF_UP)));
			}
			if(beer.ibu  != null && !beer.ibu.isEmpty()) {
				this.commandGateway.sendAndWait(new UpdateBeerIbuCommand(beerId, new BigDecimal(beer.ibu).setScale(0, HALF_UP)));
			}

			final Images bdbImages = beer.labels;
			if(bdbImages != null && (
					bdbImages.icon != null ||
					bdbImages.medium != null ||
					bdbImages.large != null)) {
				this.commandGateway.sendAndWait(new UpdateBeerImagesCommand(beerId, AvailableImages.fromString(bdbImages.icon, bdbImages.medium,bdbImages.large)));
			}

			this.commandGateway.sendAndWait(new BeerAvailableCommand(breweryId, beerId));
			return beerId;
		}

		@Override
		public synchronized void onApplicationEvent(ContextRefreshedEvent event) {
			if(completed) {
				return;
			}
			completed = true;
			UserHolder.set(SYSTEM);
			final AddUserCommand command = new AddUserCommand("FBS", "dave", "password");
			final GenericAddAggregateCallback<UserId> callback = GenericAddAggregateCallback.forCommand(command);
			this.commandGateway.send(command);
			this.commandGateway.send(new AddRoleToUserCommand(callback.identifier(), ADMIN_ROLE));
			this.commandGateway.send(new AddUserCommand("PBR", "melissa", "password"));
			final Map<String,LocationId> brewDbLocationIdMap = new HashMap<>();
			for(final Location location : this.locationMap.values()) {
				final LocationId locationId = fireLocationCommands(location);
				brewDbLocationIdMap.put(location.id, locationId);
			}
			for(final BreweryData brewery : this.breweryMap.values()) {
				final String breweryName = brewery.breweryName;
				final LocationId locationId = this.nameToLocationIdMap.get(breweryName);
				this.nameToBreweryIdMap.put(breweryName, locationId);
				final BeerSetMapping beers = this.beerMap.get(brewery.breweryId);
				if(beers != null) {
					for(final Beer beer : beers) {
						fireBeerCommands(beer, locationId, breweryName);
					}
				}
			}
			this.fireCustomCommands();
			this.associateLocationsWithBreweries();
		}



		private void associateLocationsWithBreweries() {
			for(final Entry<String, LocationId> entry : this.nameToLocationIdMap.entrySet()) {
				final LocationId locationId = entry.getValue();
				this.commandGateway.sendAndWait(new UpdateLocationBreweryAssociationCommand(locationId, locationId));
			}

		}

		private void fireCustomCommands() {
			for(final NewLocationCommands commandList : customNewLocationCommands()) {
				final AddLocationCommand addCommand = commandList.addCommand;
				final GenericAddAggregateCallback<LocationId> callback = GenericAddAggregateCallback.forCommand(addCommand);
				this.commandGateway.sendAndWait(addCommand);
				final LocationId locationId = callback.identifier();

				this.nameToLocationIdMap.put(addCommand.name, locationId);
				this.commandGateway.sendAndWait(new UpdateLocationBreweryAssociationCommand(locationId, locationId));

				for(final AbstractLocationCommand command : commandList.detailCommands) {
					this.commandGateway.sendAndWait(setLocation(locationId, command));
				}
			}
			for(final ExistingLocationCommands command : buildCustomExistingLocationCommands()) {
				final LocationId locationId = this.nameToLocationIdMap.get(command.locationName);
				this.commandGateway.sendAndWait(setLocation(locationId, command.command));
			}
			for(final NewBeerCommands commands : customAddBeerCommands()) {
				final AddBeerCommand addCommand = commands.addCommand;
				final LocationId locationId = this.nameToLocationIdMap.get(addCommand.breweryName);
				final GenericAddAggregateCallback<BeerId> callback = GenericAddAggregateCallback.forCommand(addCommand);
				this.commandGateway.sendAndWait(setField(locationId, "breweryId", addCommand));
				final BeerId beerId = callback.identifier();
				this.commandGateway.sendAndWait(new BeerAvailableCommand(locationId, beerId));
				for(final AbstractBeerCommand command : commands.detailCommands) {
					this.commandGateway.sendAndWait(setField(beerId, "beerId", command));
				}

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

		private static List<NewBeerCommands> customAddBeerCommands() {
			final List<NewBeerCommands> result = new ArrayList<>();
			result.add(new NewBeerCommands(
					new AddBeerCommand("Boss Fight Triple IPA", null, "Lucky Envelope Brewing"),
					new UpdateBeerDescriptionCommand(null, "Triple IPA Season is upon us! Intense hop flavors and aromas from heavy double dry hopping at over 2 pounds per barrel. Our 3x IPA is bigger, meaner, and has a lot more hit points than your standard IPA."),
					new UpdateBeerStyleCommand(null, "Imperial or Double India Pale Ale"),
					new UpdateBeerAbvCommand(null, new BigDecimal("10.3")),
					new UpdateBeerIbuCommand(null, new BigDecimal("104"))
					));
			result.add(new NewBeerCommands(
					new AddBeerCommand("ENIAC 2.0 Mosaic India Pale Ale", null, "Lucky Envelope Brewing"),
					new UpdateBeerDescriptionCommand(null, "Version 2.0 of our ENIAC IPA screams Mosaic hops and a balanced malt profile with notes of juicy tropical fruit and resinous pine."),
					new UpdateBeerStyleCommand(null, "Imperial or Double India Pale Ale"),
					new UpdateBeerAbvCommand(null, new BigDecimal("6.4")),
					new UpdateBeerIbuCommand(null, new BigDecimal("68"))
					));
			result.add(new NewBeerCommands(
					new AddBeerCommand("50th Street India Pale Ale", null, "Lucky Envelope Brewing"),
					new UpdateBeerDescriptionCommand(null, "Our San Diego-inspired IPA is a bitter and grapefruit-forward hoppy beer. The pilsner malt base is crisp and clean to let the hop profile shine."),
					new UpdateBeerStyleCommand(null, "American-Style India Pale Ale"),
					new UpdateBeerAbvCommand(null, new BigDecimal("6.8")),
					new UpdateBeerIbuCommand(null, new BigDecimal("80"))
					));
			result.add(new NewBeerCommands(
					new AddBeerCommand("Galaxy Session IPA", null, "Lucky Envelope Brewing"),
					new UpdateBeerDescriptionCommand(null, "This hoppy, yet sessionable beer packs the hop kick of a traditional IPA but with less alcohol. Australian Galaxy hops provide a juicy, tropical fruit aroma."),
					new UpdateBeerStyleCommand(null, "Session India Pale Ale"),
					new UpdateBeerAbvCommand(null, new BigDecimal("4.6")),
					new UpdateBeerIbuCommand(null, new BigDecimal("52"))
					));
			result.add(new NewBeerCommands(
					new AddBeerCommand("Imperial Porter", null, "Lucky Envelope Brewing"),
					new UpdateBeerDescriptionCommand(null, "The imperial robust porter packs complex malt flavor while still being able to enjoy a pint. This beer is bold and chewy with flavors of espresso, milk chocolate, and caramel."),
					new UpdateBeerStyleCommand(null, "American-Style Imperial Porter"),
					new UpdateBeerAbvCommand(null, new BigDecimal("7.5")),
					new UpdateBeerIbuCommand(null, new BigDecimal("42"))
					));
			result.add(new NewBeerCommands(
					new AddBeerCommand("Flying Envelope Washington Lager", null, "Lucky Envelope Brewing"),
					new UpdateBeerDescriptionCommand(null, "This American Craft Lager uses 100% Washington-grown ingredients and our house lager strain. Light and crisp, this beer showcases local heirloom malt with a smooth bready finish."),
					new UpdateBeerAbvCommand(null, new BigDecimal("4.7")),
					new UpdateBeerIbuCommand(null, new BigDecimal("26"))
					));
			result.add(new NewBeerCommands(
					new AddBeerCommand("Buddha’s Hand Pale Ale", null, "Lucky Envelope Brewing"),
					new UpdateBeerDescriptionCommand(null, "A classic Pacific Northwest pale ale infused with zest from Buddha’s Hand and Chinese pomelo citrus fruits. Both citrus fruits are native to southeastern Asia"),
					new UpdateBeerStyleCommand(null, "American-Style Pale Ale"),
					new UpdateBeerAbvCommand(null, new BigDecimal("5.1")),
					new UpdateBeerIbuCommand(null, new BigDecimal("38"))
					));


//			result.add(new AddBeerCommand("Pigeonhole IPA", "IPA with Chinook, Citra, Amarillo, and LOTS of Simcoe", null, "Cloudburst Brewing", null, "American-Style India Pale Ale", null, null));
//			result.add(new AddBeerCommand("Born Again", "Strong Blonde Ale spiced with grains of paradise, long peppercorns, & coriander", null, "Cloudburst Brewing", null, null, null, null));
//			result.add(new AddBeerCommand("Punch Drunk Love", "Strong Pale Ale with Sweet Cherry puree and Pineapple juice.", null, "Cloudburst Brewing", null, null, null, null));
//			result.add(new AddBeerCommand("Psycho Hose Beast", "Triple IPA", null, "Cloudburst Brewing", null, null, null, null));
//			result.add(new AddBeerCommand("Market Fresh Saison", "Version 2: Grapefruit & Rosemary", null, "Cloudburst Brewing", null, null, null, null));
//			result.add(new AddBeerCommand("Aw Shucks", "Oyster Stout", null, "Cloudburst Brewing", null, null, null, null));
//			result.add(new AddBeerCommand("Unreliable Narrator", "Dry Hopped Pale with GR Mandarina & NZ Nelson Sauvin hops", null, "Cloudburst Brewing", null, null, null, null));
//			result.add(new AddBeerCommand("Cure-All", "Nitro Milk Stout", null, "Cloudburst Brewing", null, null, null, null));
//			result.add(new AddBeerCommand("Hotline Bling IPA", "IPA with Mosaic, Galaxy, & El Dorado", null, "Cloudburst Brewing", null, null, null, null));
//			result.add(new AddBeerCommand("Hoppy Little Clouds", "Dry-Hopped Pilsner", null, "Cloudburst Brewing", null, null, null, null));
			return result;
		}

		private static Collection<NewLocationCommands> customNewLocationCommands() {
//			final NewLocationCommands cloudburst = new NewLocationCommands(new AddLocationCommand("Cloudburst Brewing"),
//					new UpdateLocationAddressCommand(null, "2116 Western Ave", null, "Seattle", "WA", "98121"),
//					new UpdateLocationPositionCommand(null, new BigDecimal("47.6112849"), new BigDecimal("-122.345076")),
//					new UpdateLocationWebsiteCommand(null, "http://cloudburstbrew.com/"),
//					new UpdateLocationImagesCommand(null, AvailableImages.fromString(null, "resources/img/breweries/initial/cloudburst.png", null))
//					);
//			return Collections.singletonList(cloudburst);
			return Collections.emptyList();
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
		private static class NewBeerCommands {
			public final AddBeerCommand addCommand;
			public final List<AbstractBeerCommand> detailCommands;
			public NewBeerCommands(AddBeerCommand addCommand, AbstractBeerCommand... detailCommands) {
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
