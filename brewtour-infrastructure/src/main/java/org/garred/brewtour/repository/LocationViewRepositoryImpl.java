package org.garred.brewtour.repository;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.view.LocationView;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LocationViewRepositoryImpl extends AbstractViewRepository<LocationId, LocationView> implements LocationViewRepository {

	private final String findAll;

	@Autowired
	public LocationViewRepositoryImpl(DataSource datasource, ObjectMapper objectMapper) {
		super("location_view", LocationView.class, datasource, objectMapper);
		this.findAll = format("SELECT * FROM %s.%s",SCHEMA,"location_view");
	}

	@Override
	public Collection<LocationView> findLocationsWithBeer(BeerId beerId) {
		final List<LocationView> result = new ArrayList<>();
		for(final LocationView view : this.jdbcTemplate.query(this.findAll, this.rowMapperExtractor)) {
			if(view != null && view.beers.contains(beerId)) {
				result.add(view);
			}
		}
		return result;
	}

}
