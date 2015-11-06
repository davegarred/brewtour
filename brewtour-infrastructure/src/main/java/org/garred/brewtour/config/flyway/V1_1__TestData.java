package org.garred.brewtour.config.flyway;

import static org.garred.brewtour.application.AvailableImages.NO_IMAGES;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
import org.garred.brewtour.application.AvailableImages;
import org.garred.brewtour.application.Beer;
import org.garred.brewtour.application.Image;
import org.garred.brewtour.application.Location;
import org.garred.brewtour.application.LocationId;
import org.garred.brewtour.brewdb.BrewDbLocation;
import org.garred.brewtour.brewdb.BrewDbLocation.BrewDbLocationList;
import org.garred.brewtour.infrastructure.ObjectMapperFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class V1_1__TestData implements JdbcMigration {

	private static int i = 0;

	@Override
	public void migrate(Connection connection) throws Exception {
		final ObjectMapper objectMapper = ObjectMapperFactory.objectMapper();
		final InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("json/test_locations.json");
		final BrewDbLocationList locations = objectMapper.readValue(in, BrewDbLocationList.class);
		for(final BrewDbLocation brewDbLocation : locations) {
			final Location location = convert(brewDbLocation);
			final PreparedStatement statement = connection.prepareStatement("INSERT INTO brewtour.location(id,version,data) VALUES (?,1,?)");
			statement.setString(1, location.locationId.id);
			statement.setString(2, objectMapper.writeValueAsString(location));
			statement.executeUpdate();
		}
	}

	private static Location convert(BrewDbLocation brewDbLocation) {
		final LocationId id = new LocationId(String.format("LOCA%4d", new Integer(i++)));
		final Map<String,String> bdbImages = brewDbLocation.images;
		final AvailableImages images = bdbImages == null ? NO_IMAGES : new AvailableImages(new Image(bdbImages.get("icon")), new Image(bdbImages.get("medium")), new Image(bdbImages.get("large")));
		final List<Beer> beers = new ArrayList<>();
		if(brewDbLocation.beerIds == null) {
			for(final String beerId : brewDbLocation.beerIds) {
				beers.add(new Beer(beerId));
			}
		}
		return new Location(id,
				brewDbLocation.id,
				brewDbLocation.breweryName,
				brewDbLocation.breweryDescription,
				brewDbLocation.latitude,
				brewDbLocation.longitude,
				images,
				beers);
	}


}
