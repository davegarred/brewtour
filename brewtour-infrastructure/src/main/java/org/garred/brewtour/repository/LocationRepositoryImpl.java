package org.garred.brewtour.repository;

import javax.sql.DataSource;

import org.garred.brewtour.application.Location;
import org.garred.brewtour.application.LocationId;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LocationRepositoryImpl extends AbstractObjectRepository<LocationId,Location> implements LocationRepository {

	public LocationRepositoryImpl(DataSource datasource, ObjectMapper objectMapper) {
		super("brewtour", "location", Location.class, datasource, objectMapper);
	}

}
