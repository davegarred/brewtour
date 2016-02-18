package org.garred.brewtour.config;

import static java.util.stream.Collectors.toList;
import static org.garred.brewtour.config.BrewDbDataPrep.BEER_FILE;
import static org.garred.brewtour.config.BrewDbDataPrep.BREWERY_FILE;
import static org.garred.brewtour.config.BrewDbDataPrep.LOCATION_FILE;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.garred.brewdb.domain.Beer;
import org.garred.brewdb.domain.Images;
import org.garred.brewdb.domain.Location;
import org.garred.brewtour.application.IdentifierFactory;
import org.garred.brewtour.application.command.beer.AddBeerCommand;
import org.garred.brewtour.application.command.brewery.AddBreweryCommand;
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
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CommandInitializer implements ApplicationListener<ContextRefreshedEvent> {

		private static volatile boolean completed = false;

		private final ObjectMapper objectMapper = ObjectMapperFactory.objectMapper();

		public final Map<String,Location> locationMap;
		public final Map<String,BreweryData> breweryMap;
		public final Map<String,BeerSetMapping> beerMap;

		private final CommandGateway commandGateway;
		private final IdentifierFactory<LocationId> locationIdentifierFactory;
		private final IdentifierFactory<BreweryId> breweryIdentifierFactory;
		private final IdentifierFactory<BeerId> beerIdentifierFactory;

		public CommandInitializer(CommandGateway commandGateway,
				IdentifierFactory<LocationId> locationIdentifierFactory,
				IdentifierFactory<BreweryId> breweryIdentifierFactory,
				IdentifierFactory<BeerId> beerIdentifierFactory) {
			this.commandGateway = commandGateway;
			this.locationIdentifierFactory = locationIdentifierFactory;
			this.breweryIdentifierFactory = breweryIdentifierFactory;
			this.beerIdentifierFactory = beerIdentifierFactory;
			try(
					final InputStream locationFile = Thread.currentThread().getContextClassLoader().getResourceAsStream(LOCATION_FILE);
					final InputStream breweryFile = Thread.currentThread().getContextClassLoader().getResourceAsStream(BREWERY_FILE);
					final InputStream beerFile = Thread.currentThread().getContextClassLoader().getResourceAsStream(BEER_FILE)
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

		private LocationId fireLocationCommands(Location brewDbLocation) {
			final AddLocationCommand command = new AddLocationCommand(brewDbLocation.brewery.name);
			this.commandGateway.sendAndWait(command);
			final LocationId locationId = this.locationIdentifierFactory.last();

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
			this.commandGateway.sendAndWait(new AddBreweryCommand(brewery.breweryName));
			return this.breweryIdentifierFactory.last();
		}
		private BeerId fireBeerCommands(Beer beer, BreweryId breweryId, String breweryName, List<LocationId> locationIds) {
			this.commandGateway.sendAndWait(new AddBeerCommand(beer.name, breweryId, breweryName,
					beer.style == null ? null : beer.style.name,
					(beer.style == null || beer.style.category == null) ? null : beer.style.category.name,
					beer.abv == null ? null : new BigDecimal(beer.abv),
					beer.ibu == null ? null : new BigDecimal(beer.ibu)));
			final BeerId beerId = this.beerIdentifierFactory.last();
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
		}

	}
