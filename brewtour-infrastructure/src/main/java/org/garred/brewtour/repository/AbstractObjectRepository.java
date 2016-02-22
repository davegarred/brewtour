package org.garred.brewtour.repository;

import static java.lang.String.format;
import static java.sql.Types.CLOB;
import static java.sql.Types.INTEGER;
import static java.sql.Types.VARCHAR;
import static java.util.Arrays.asList;

import java.io.IOException;
import java.io.Reader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.garred.brewtour.domain.Entity;
import org.garred.brewtour.domain.Identifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractObjectRepository<I extends Identifier,T extends Entity<I>> implements ViewRepository<I,T> {

	protected static final String SCHEMA = "brewtour";

	protected final ResultSetExtractor<T> resultSetExtractor = new ResultSetExtractor<T>() {
		@Override
		public T extractData(ResultSet rs) throws SQLException, DataAccessException {
			if(!rs.next()) {
				return null;
			}
			final Reader data = rs.getCharacterStream("data");
			return deserialize(data);
		}
	};
	protected final RowMapper<T> rowMapperExtractor = new RowMapper<T>() {
		@Override
		public T mapRow(ResultSet rs, int rowNum) throws SQLException {
			final Reader data = rs.getCharacterStream("data");
			return deserialize(data);
		}
	};

	private final PreparedStatementCreatorFactory findOne;
	private final PreparedStatementCreatorFactory insert;
	private final PreparedStatementCreatorFactory update;
	private final PreparedStatementCreatorFactory deleteOne;
	protected final String findVersionQuery;

	protected final JdbcTemplate jdbcTemplate;
	protected final ObjectMapper objectMapper;
	protected final Class<T> clazz;

	public AbstractObjectRepository(String table, Class<T> clazz, DataSource datasource, ObjectMapper objectMapper) {
		this.clazz = clazz;
		this.jdbcTemplate = new JdbcTemplate(datasource);
		this.objectMapper = objectMapper;
		this.findOne = new PreparedStatementCreatorFactory(format("SELECT * FROM %s.%s WHERE id=?",SCHEMA,table), VARCHAR);
		this.insert = new PreparedStatementCreatorFactory(format("INSERT INTO %s.%s(id,version,data) values (?,?,?)",SCHEMA,table), VARCHAR, INTEGER, CLOB);
		this.update = new PreparedStatementCreatorFactory(format("UPDATE %s.%s SET version=?, data=? WHERE id=? AND version=?",SCHEMA,table), INTEGER, CLOB, VARCHAR, INTEGER);
		this.deleteOne = new PreparedStatementCreatorFactory(format("DELETE FROM %s.%s WHERE id=?",SCHEMA,table), VARCHAR);
		this.findVersionQuery = format("SELECT version FROM %s.%s WHERE id=?",SCHEMA,table);
	}

	@Override
	public void save(T value) {
		final List<Object> params = new ArrayList<>();
		final I key = value.identifier();
		params.add(key.getId());
		params.add(new Integer(1));
		params.add(serialize(value));
		this.jdbcTemplate.update(this.insert.newPreparedStatementCreator(params));
	}

	@Override
	public boolean exists(I key) {
		try {
			final Integer version = this.jdbcTemplate.queryForObject(this.findVersionQuery, Integer.class, key.getId());
			return version != null;
		} catch(final EmptyResultDataAccessException e) {
			return false;
		}
	}

	@Override
	public void update(T value) throws ObjectDoesNotExistException {
		int version;
		final I key = value.identifier();
		try {
			version = this.jdbcTemplate.queryForObject(this.findVersionQuery, Integer.class, key.getId()).intValue();
		} catch(final EmptyResultDataAccessException e) {
			throw new ObjectDoesNotExistException(this.clazz, key.getId(), e);
		}
		final List<Object> params = asList(new Integer(version+1), serialize(value), key.getId(), new Integer(version));
		final int result = this.jdbcTemplate.update(this.update.newPreparedStatementCreator(params));
		if(result != 1) {
			throw new OptimisticLockingException(this.clazz, key.getId());
		}
	}

	@Override
	public T get(I key) {
		return this.jdbcTemplate.query(this.findOne.newPreparedStatementCreator(asList(key.getId())), this.resultSetExtractor);
	}

	@Override
	public T require(I key) throws ObjectDoesNotExistException {
		final T object = get(key);
		if(object == null) {
			throw new ObjectDoesNotExistException(this.clazz, key.getId());
		}
		return object;
	}

	@Override
	public void delete(I key) {
		this.jdbcTemplate.update(this.deleteOne.newPreparedStatementCreator(asList(key.getId())));
	}

	protected String serialize(Object value) {
		try {
			return this.objectMapper.writeValueAsString(value);
		} catch (final JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	protected T deserialize(Reader reader) {
		try {
			return this.objectMapper.readValue(reader, this.clazz);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

}
