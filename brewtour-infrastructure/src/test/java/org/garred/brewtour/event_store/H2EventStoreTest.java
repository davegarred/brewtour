package org.garred.brewtour.event_store;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.axonframework.domain.DomainEventMessage;
import org.axonframework.domain.DomainEventStream;
import org.axonframework.domain.GenericDomainEventMessage;
import org.axonframework.domain.SimpleDomainEventStream;
import org.axonframework.eventstore.EventStore;
import org.garred.brewtour.application.LocationId;
import org.garred.brewtour.application.UserId;
import org.garred.brewtour.application.event.FavoriteLocationsUpdatedEvent;
import org.garred.brewtour.application.event.UserAddedEvent;
import org.garred.brewtour.infrastructure.ObjectMapperFactory;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/persistence-config.xml")
public class H2EventStoreTest {

	private static final LocationId LOCATION_ID = new LocationId("a location id");
	private static final HashSet<LocationId> FAVORITE_LOCATIONS = new HashSet<>(asList(LOCATION_ID));
	private static final LocalDateTime LOCAL_DATE_TIME = new LocalDateTime(2013, 1, 24, 10, 44);
	private static final String TYPE = "User";
	private static final UserId USER_ID = new UserId("a user id");
	private static final FavoriteLocationsUpdatedEvent FAVORITE_LOCATIONS_UPDATED_EVENT = new FavoriteLocationsUpdatedEvent(USER_ID, FAVORITE_LOCATIONS);

	private static final UserAddedEvent USER_ADDED_EVENT = new UserAddedEvent(USER_ID, "login");


	private final ObjectMapper objectMapper = ObjectMapperFactory.objectMapper();
	private int currentEvent = 0;

	@Autowired
	private DataSource datasource;

	private EventStore eventStore;

	@Before
	public void setup() {
		try (Connection conn = this.datasource.getConnection()) {
			conn.prepareStatement("DELETE FROM brewtour.event_store").executeUpdate();
		} catch (final SQLException e) {
			throw new RuntimeException(e);
		}
		this.eventStore = new H2EventStore(this.datasource, this.objectMapper);
		this.currentEvent = 0;
	}

	@Test
	public void testNoEvents() {
		final DomainEventStream events = this.eventStore.readEvents(TYPE, USER_ID);
		assertTrue(!events.hasNext());
	}
	@Test
	public void testAppendEvents() {
		final List<DomainEventMessage<?>> appendEvents = eventList(USER_ADDED_EVENT);
		this.eventStore.appendEvents(TYPE, new SimpleDomainEventStream(appendEvents));

		final DomainEventStream events = this.eventStore.readEvents(TYPE, USER_ID);
		final DomainEventMessage<?> event = events.next();
		assertReflectionEquals(USER_ADDED_EVENT, event.getPayload());
		assertTrue(!events.hasNext());
	}
	@Test
	public void testAppendEventsAgain() {
		this.eventStore.appendEvents(TYPE, new SimpleDomainEventStream(eventList(USER_ADDED_EVENT)));
		this.eventStore.appendEvents(TYPE, new SimpleDomainEventStream(eventList(FAVORITE_LOCATIONS_UPDATED_EVENT)));

		final DomainEventStream events = this.eventStore.readEvents(TYPE, USER_ID);
		assertReflectionEquals(USER_ADDED_EVENT, events.next().getPayload());
		assertReflectionEquals(FAVORITE_LOCATIONS_UPDATED_EVENT, events.next().getPayload());
		assertTrue(!events.hasNext());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<DomainEventMessage<?>> eventList(Object... events) {
		final Map<String, ?> metaData = metaData();
		final List<DomainEventMessage<?>> appendEvents = new ArrayList<>();
		for(final Object event : events) {
			appendEvents.add(new GenericDomainEventMessage(USER_ID, this.currentEvent++, event, metaData));
		}
		return appendEvents;
	}

	private static void assertReflectionEquals(Object a, Object b) {
		assertTrue(reflectionEquals(a, b, true));
	}

	private static Map<String, ?> metaData() {
		final Map<String,Object> result = new HashMap<>();
		result.put("currentUser", USER_ID);
		result.put("submitTime", LOCAL_DATE_TIME);
		return result;
	}

}
