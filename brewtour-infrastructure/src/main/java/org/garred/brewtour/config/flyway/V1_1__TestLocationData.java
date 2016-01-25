package org.garred.brewtour.config.flyway;

import static java.util.Collections.emptyList;
import static org.garred.brewtour.application.AvailableImages.NO_IMAGES;
import static org.garred.brewtour.application.LocaleId.SEATTLE;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
import org.garred.brewtour.application.AvailableImages;
import org.garred.brewtour.application.Beer;
import org.garred.brewtour.application.GoogleMapsParameters;
import org.garred.brewtour.application.GoogleMapsParameters.GoogleMapsPosition;
import org.garred.brewtour.application.Image;
import org.garred.brewtour.application.Locale;
import org.garred.brewtour.application.LocalePoint;
import org.garred.brewtour.application.Location;
import org.garred.brewtour.application.LocationId;
import org.garred.brewtour.brewdb.BrewDbBeer;
import org.garred.brewtour.brewdb.BrewDbBeer.BrewDbBeerList;
import org.garred.brewtour.brewdb.BrewDbBeer.Style;
import org.garred.brewtour.brewdb.BrewDbLocation;
import org.garred.brewtour.brewdb.BrewDbLocation.BrewDbLocationList;
import org.garred.brewtour.infrastructure.ObjectMapperFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class V1_1__TestLocationData implements JdbcMigration {

	private static int i = 1000;
	private static Map<String, Beer> beerMap = new HashMap<>();

	@Override
	public void migrate(Connection connection) throws Exception {
		final ObjectMapper objectMapper = ObjectMapperFactory.objectMapper();
		loadBeerData(objectMapper);
		final List<LocalePoint> seattleLocales = new ArrayList<>();
		final InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("json/test_locations.json");
		final BrewDbLocationList locations = objectMapper.readValue(in, BrewDbLocationList.class);
		for (final BrewDbLocation brewDbLocation : locations) {
			final Location location = convertToLocation(brewDbLocation);
			final PreparedStatement statement = connection
					.prepareStatement("INSERT INTO brewtour.location(id,version,data) VALUES (?,1,?)");
			statement.setString(1, location.getIdentifier().id);
			statement.setString(2, objectMapper.writeValueAsString(location));
			statement.executeUpdate();
			seattleLocales.add(convertToLocalePoint(location));
		}
		final GoogleMapsParameters params = new GoogleMapsParameters(new GoogleMapsPosition(new BigDecimal("47.61"), new BigDecimal("-122.333")),
				12);
		final Locale seattle = new Locale(SEATTLE, "Seattle", params, seattleLocales);
		final PreparedStatement statement = connection.prepareStatement("INSERT INTO brewtour.locale(id,version,data) VALUES (?,1,?)");
		statement.setString(1, seattle.getIdentifier().id);
		statement.setString(2, objectMapper.writeValueAsString(seattle));
		statement.executeUpdate();
	}

	private static Location convertToLocation(BrewDbLocation brewDbLocation) {
		final LocationId id = new LocationId(String.format("LOCA%4d", new Integer(i++)));
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
		return new Location(id, brewDbLocation.id, brewDbLocation.brewery.name, brewDbLocation.brewery.description, brewDbLocation.latitude,
				brewDbLocation.longitude, images, beers, emptyList());
	}

	private static LocalePoint convertToLocalePoint(Location location) {
		return new LocalePoint(location.getIdentifier(), location.getName(), location.getLatitude(), location.getLongitude(), location.getImages());
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
		System.out.println(objectMapper.writeValueAsString(bdbStyles));
		System.out.println();
	}

	private static Beer convert(BrewDbBeer beer) {
		return new Beer(beer.id, beer.name, beer.status, beer.style == null ? "" : beer.style.name,
				beer.style == null ? "" : beer.style.category == null ? "" : beer.style.category.name,
				beer.abv, beer.ibu, true, emptyList());
	}
}
