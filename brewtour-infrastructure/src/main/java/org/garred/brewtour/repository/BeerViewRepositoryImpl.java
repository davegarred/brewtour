package org.garred.brewtour.repository;

import javax.sql.DataSource;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.view.BeerView;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BeerViewRepositoryImpl extends AbstractViewRepository<BeerId, BeerView> implements BeerViewRepository {

	@Autowired
	public BeerViewRepositoryImpl(DataSource datasource, ObjectMapper objectMapper) {
		super("beer_view", BeerView.class, datasource, objectMapper);
	}

}
