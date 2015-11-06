package org.garred.brewtour.repository;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import org.garred.brewtour.application.Location;
import org.garred.brewtour.application.LocationId;
import org.springframework.jdbc.core.RowMapper;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LocationRepositoryImpl extends AbstractObjectRepository<LocationId,Location> implements LocationRepository {

	private final RowMapper<Location> locationRowMapper = new RowMapper<Location>() {
		@Override
		public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
			final Clob data = rs.getClob("data");
			return deserialize(data.getCharacterStream());
		}
	};

	public LocationRepositoryImpl(DataSource datasource, ObjectMapper objectMapper) {
		super("brewtour", "location", Location.class, datasource, objectMapper);
	}

	@Override
	public Collection<Location> findAll() {
		return this.jdbcTemplate.query("SELECT * FROM brewtour.location", this.locationRowMapper);
	}

}
