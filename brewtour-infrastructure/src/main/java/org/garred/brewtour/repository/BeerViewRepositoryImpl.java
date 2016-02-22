package org.garred.brewtour.repository;

import static java.lang.String.format;

import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.view.BeerView;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BeerViewRepositoryImpl extends AbstractViewRepository<BeerId, BeerView> implements BeerViewRepository {

	private final String findMany = format("SELECT * FROM %s.%s WHERE id in ({ids})",SCHEMA,"beer_view");

	@Autowired
	public BeerViewRepositoryImpl(DataSource datasource, ObjectMapper objectMapper) {
		super("beer_view", BeerView.class, datasource, objectMapper);
	}

	@Override
	public List<BeerView> getAll(List<BeerId> beers) {
		final String beerList = beers.stream().map(b -> "'" + b.id + "'").collect(Collectors.joining(","));
		return this.jdbcTemplate.query(this.findMany.replace("{ids}", beerList), this.rowMapperExtractor);
	}

}
