package org.garred.brewtour.repository;

import javax.sql.DataSource;

import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.view.LocationView;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LocationViewRepositoryImpl extends AbstractViewRepository<LocationId, LocationView> implements LocationViewRepository {

	@Autowired
	public LocationViewRepositoryImpl(DataSource datasource, ObjectMapper objectMapper) {
		super("location_view", LocationView.class, datasource, objectMapper);
	}

}
