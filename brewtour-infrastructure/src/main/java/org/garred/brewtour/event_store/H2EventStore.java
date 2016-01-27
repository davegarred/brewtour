package org.garred.brewtour.event_store;

import static java.lang.String.format;
import static java.sql.Types.BIGINT;
import static java.sql.Types.CLOB;
import static java.sql.Types.VARCHAR;

import java.io.IOException;
import java.io.Reader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.axonframework.domain.DomainEventMessage;
import org.axonframework.domain.DomainEventStream;
import org.axonframework.domain.GenericDomainEventMessage;
import org.axonframework.domain.SimpleDomainEventStream;
import org.axonframework.eventstore.EventStore;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class H2EventStore implements EventStore {

	private static final String SCHEMA = "brewtour";

	private final JdbcTemplate jdbcTemplate;
	private final ObjectMapper objectMapper;
	private final PreparedStatementCreatorFactory findAll;
	private final PreparedStatementCreatorFactory insert;

	public H2EventStore(DataSource datasource, ObjectMapper objectMapper) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
		this.objectMapper = objectMapper;
		this.findAll = new PreparedStatementCreatorFactory(format("SELECT * FROM %s.event_store WHERE agg_type=? AND id=? ORDER BY sequence_number",SCHEMA), VARCHAR, VARCHAR);
		this.insert = new PreparedStatementCreatorFactory(
				format("INSERT INTO %s.event_store(agg_type, id, sequence_number, payload_type, payload, metadata) "
						+ "values (?,?,?,?,?,?)",SCHEMA), VARCHAR, VARCHAR, BIGINT, VARCHAR, CLOB,  CLOB);

	}

	@Override
	public void appendEvents(String type, DomainEventStream events) {
		while(events.hasNext()) {
			final DomainEventMessage<?> event = events.next();
			final List<Object> params = new ArrayList<>();
			params.add(type);
			params.add(event.getAggregateIdentifier().toString());
			params.add(new Long(event.getSequenceNumber()));
			params.add(event.getPayloadType().getCanonicalName());
			params.add(serialize(event.getPayload()));
			params.add(serialize(event.getMetaData()));
			this.jdbcTemplate.update(this.insert.newPreparedStatementCreator(params));
		}
	}

	@Override
	public DomainEventStream readEvents(String type, Object identifier) {
		final ResultSetExtractor<DomainEventStream> extractor = new ResultSetExtractor<DomainEventStream>() {
			@Override
			public DomainEventStream extractData(ResultSet rs) throws SQLException, DataAccessException {
				final List<DomainEventMessage<?>> messages = new ArrayList<>();
				while(rs.next()) {
					Class<?> payloadType;
					try {
						final long sequenceNumber = rs.getLong("sequence_number");
						payloadType = Class.forName(rs.getString("payload_type"));
						final Reader data = rs.getCharacterStream("payload");
						final Object payload = deserialize(data, payloadType);
						final Map<?,?> metaData = deserialize(rs.getCharacterStream("metadata"), Map.class);
						final DomainEventMessage<?> message = new GenericDomainEventMessage(identifier, sequenceNumber, payload, metaData);
						messages.add(message);
					} catch (final ClassNotFoundException e) {
						throw new RuntimeException(e);
					}
				}
				return new SimpleDomainEventStream(messages);
			}
		};
		final List<Object> params = new ArrayList<>();
		params.add(type);
		params.add(identifier.toString());
		return this.jdbcTemplate.query(this.findAll.newPreparedStatementCreator(params), extractor);
	}

	protected String serialize(Object value) {
		try {
			return this.objectMapper.writeValueAsString(value);
		} catch (final JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	protected <T> T deserialize(Reader reader, Class<T> clazz) {
		try {
			return this.objectMapper.readValue(reader, clazz);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

}
