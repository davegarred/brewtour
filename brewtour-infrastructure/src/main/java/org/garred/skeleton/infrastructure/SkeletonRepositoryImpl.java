package org.garred.skeleton.infrastructure;

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

import org.garred.skeleton.api.SkelAggregate;
import org.garred.skeleton.api.SkelId;
import org.garred.skeleton.repository.SkeletonRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SkeletonRepositoryImpl implements SkeletonRepository {

	private static final PreparedStatementCreatorFactory FIND_ONE = new PreparedStatementCreatorFactory("SELECT * FROM brewtour.skeleton WHERE id=?", VARCHAR);
	private static final PreparedStatementCreatorFactory INSERT = new PreparedStatementCreatorFactory("INSERT INTO brewtour.skeleton(id,version,data) values (?,?,?)", VARCHAR, INTEGER, CLOB);
	private static final PreparedStatementCreatorFactory UPDATE = new PreparedStatementCreatorFactory("UPDATE brewtour.skeleton SET version=?, data=? WHERE id=? AND version=?", INTEGER, CLOB, VARCHAR, INTEGER);
	private static final PreparedStatementCreatorFactory DELETE_ONE = new PreparedStatementCreatorFactory("DELETE FROM brewtour.skeleton WHERE id=?", VARCHAR);

	private final JdbcTemplate jdbcTemplate;
	private final ObjectMapper objectMapper;

	public SkeletonRepositoryImpl(DataSource datasource, ObjectMapper objectMapper) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
		this.objectMapper = objectMapper;
	}

	@Override
	public void save(SkelId key, SkelAggregate value) {
		final List<Object> params = new ArrayList<>();
		params.add(key.id);
		params.add(new Integer(1));
		params.add(serialize(value));
		this.jdbcTemplate.update(INSERT.newPreparedStatementCreator(params));
	}

	@Override
	public boolean exists(SkelId key) {
		try {
			final Integer version = this.jdbcTemplate.queryForObject("SELECT version FROM brewtour.skeleton WHERE id=?", Integer.class, key.id);
			return version != null;
		} catch(@SuppressWarnings("unused") final EmptyResultDataAccessException e) {
			return false;
		}
	}

	@SuppressWarnings("boxing")
	@Override
	public void update(SkelId key, SkelAggregate value) {
		int version;
		try {
			version = this.jdbcTemplate.queryForObject("SELECT version FROM brewtour.skeleton WHERE id=?", Integer.class, key.id);
		} catch(final EmptyResultDataAccessException e) {
			throw new ObjectDoesNotExistException(SkelAggregate.class, key.id, e);
		}
		final List<Object> params = Arrays.asList(version+1, serialize(value), key.id, version);
		final int result = this.jdbcTemplate.update(UPDATE.newPreparedStatementCreator(params));
		if(result != 1) {
			throw new OptimisticLockingException(SkelAggregate.class, key.id);
		}
	}

	@Override
	public SkelAggregate get(SkelId key) {
		final ResultSetExtractor<SkelAggregate> extractor = new ResultSetExtractor<SkelAggregate>() {
			@Override
			public SkelAggregate extractData(ResultSet rs) throws SQLException, DataAccessException {
				rs.next();
				final Reader data = rs.getCharacterStream("data");
				return deserialize(data);
			}
		};
		return this.jdbcTemplate.query(FIND_ONE.newPreparedStatementCreator(asList(key.id)), extractor);
	}

	@Override
	public void delete(SkelId key) {
		this.jdbcTemplate.update(DELETE_ONE.newPreparedStatementCreator(asList(key.id)));
	}

	private String serialize(SkelAggregate value) {
		try {
			return this.objectMapper.writeValueAsString(value);
		} catch (final JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	private SkelAggregate deserialize(Reader reader) {
		try {
			return this.objectMapper.readValue(reader, SkelAggregate.class);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

}
