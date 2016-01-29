package org.garred.brewtour.application;

import javax.sql.DataSource;

import org.garred.brewtour.domain.LocationId;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class H2LocationIdentifierFactory implements IdentifierFactory<LocationId> {

	private static final String NEXTVAL = "SELECT NEXTVAL('locationSequence')";
	private static final String CURRVAL = "SELECT CURRVAL('locationSequence')";

	private final JdbcTemplate jdbcTemplate;

	public H2LocationIdentifierFactory(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public LocationId next() {
		final SqlRowSet res = this.jdbcTemplate.queryForRowSet(NEXTVAL);
		res.next();
		return new LocationId("LOCA" + res.getInt(1));
	}

	@Override
	public LocationId last() {
		final SqlRowSet res = this.jdbcTemplate.queryForRowSet(CURRVAL);
		res.next();
		return new LocationId("LOCA" + res.getInt(1));
	}

}
