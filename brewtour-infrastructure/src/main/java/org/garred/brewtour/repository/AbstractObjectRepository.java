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
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.garred.brewtour.application.Identifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractObjectRepository<I extends Identifier,T> implements Repository<I,T> {

	private final PreparedStatementCreatorFactory findOne;
	private final PreparedStatementCreatorFactory insert;
	private final PreparedStatementCreatorFactory update;
	private final PreparedStatementCreatorFactory deleteOne;
	private final String findVersionQuery;

	protected final JdbcTemplate jdbcTemplate;
	protected final ObjectMapper objectMapper;
	protected final Class<T> clazz;

	public AbstractObjectRepository(String schema, String table, Class<T> clazz, DataSource datasource, ObjectMapper objectMapper) {
		this.clazz = clazz;
		this.jdbcTemplate = new JdbcTemplate(datasource);
		this.objectMapper = objectMapper;
		this.findOne = new PreparedStatementCreatorFactory(format("SELECT * FROM %s.%s WHERE id=?",schema,table), VARCHAR);
		this.insert = new PreparedStatementCreatorFactory(format("INSERT INTO %s.%s(id,version,data) values (?,?,?)",schema,table), VARCHAR, INTEGER, CLOB);
		this.update = new PreparedStatementCreatorFactory(format("UPDATE %s.%s SET version=?, data=? WHERE id=? AND version=?",schema,table), INTEGER, CLOB, VARCHAR, INTEGER);
		this.deleteOne = new PreparedStatementCreatorFactory(format("DELETE FROM %s.%s WHERE id=?",schema,table), VARCHAR);
		this.findVersionQuery = format("SELECT version FROM %s.%s WHERE id=?",schema,table);
	}

	@Override
	public void save(I key, T value) {
		final List<Object> params = new ArrayList<>();
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
		} catch(@SuppressWarnings("unused") final EmptyResultDataAccessException e) {
			return false;
		}
	}

	@SuppressWarnings("boxing")
	@Override
	public void update(I key, T value) {
		int version;
		try {
			version = this.jdbcTemplate.queryForObject(this.findVersionQuery, Integer.class, key.getId());
		} catch(final EmptyResultDataAccessException e) {
			throw new ObjectDoesNotExistException(this.clazz, key.getId(), e);
		}
		final List<Object> params = Arrays.asList(version+1, serialize(value), key.getId(), version);
		final int result = this.jdbcTemplate.update(this.update.newPreparedStatementCreator(params));
		if(result != 1) {
			throw new OptimisticLockingException(this.clazz, key.getId());
		}
	}

	@Override
	public T get(I key) {
		final ResultSetExtractor<T> extractor = new ResultSetExtractor<T>() {
			@Override
			public T extractData(ResultSet rs) throws SQLException, DataAccessException {
				rs.next();
				final Reader data = rs.getCharacterStream("data");
				return deserialize(data);
			}
		};
		return this.jdbcTemplate.query(this.findOne.newPreparedStatementCreator(asList(key.getId())), extractor);
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
