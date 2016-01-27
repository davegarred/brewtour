package org.garred.brewtour.application.cqrs;

import static java.util.Arrays.asList;

import java.util.HashSet;
import java.util.Set;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.garred.brewtour.api.AddFavoriteLocationCommand;
import org.garred.brewtour.api.AddRoleToUserCommand;
import org.garred.brewtour.api.AddUserCommand;
import org.garred.brewtour.api.RemoveFavoriteLocationCommand;
import org.garred.brewtour.api.RemoveRoleFromUserCommand;
import org.garred.brewtour.application.LocationId;
import org.garred.brewtour.application.UserId;
import org.garred.brewtour.application.event.FavoriteLocationsUpdatedEvent;
import org.garred.brewtour.application.event.UserAddedEvent;
import org.garred.brewtour.application.event.UserRolesUpdatedEvent;
import org.garred.brewtour.security.GuestUserAuth;
import org.garred.brewtour.security.UserAuth;
import org.garred.brewtour.security.UserHolder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserTest {

	private static final String ROLE = "org.garred.brewtour.SomeRole";
	private static final String ROLE_2 = "org.garred.brewtour.SomeOtherRole";
	private static final LocationId LOCATION_ID = new LocationId("a location id");
	private static final LocationId LOCATION_ID_2 = new LocationId("another location id");
	private static final String LOGIN = "a user login";
	private static final UserId USER_ID = new UserId("a user id");
	private static final RemoveRoleFromUserCommand REMOVE_ROLE_FROM_USER_COMMAND = new RemoveRoleFromUserCommand(USER_ID, ROLE);
	private static final AddRoleToUserCommand ADD_ROLE_TO_USER_COMMAND = new AddRoleToUserCommand(USER_ID, ROLE);
	private static final AddRoleToUserCommand ADD_ROLE_TO_USER_COMMAND_2 = new AddRoleToUserCommand(USER_ID, ROLE_2);
	private static final UserAuth USER_AUTH = new GuestUserAuth(USER_ID);

	private static final AddUserCommand ADD_USER_COMMAND = new AddUserCommand(LOGIN);
	private static final RemoveFavoriteLocationCommand REMOVE_FAV_LOCATION_COMMAND = new RemoveFavoriteLocationCommand(LOCATION_ID);
	private static final AddFavoriteLocationCommand ADD_FAV_LOCATION_COMMAND = new AddFavoriteLocationCommand(LOCATION_ID);
	private static final AddFavoriteLocationCommand ADD_FAV_LOCATION_COMMAND_2 = new AddFavoriteLocationCommand(LOCATION_ID_2);

	private FixtureConfiguration<User> fixture;

	@Before
	public void setup() {
		this.fixture = Fixtures.newGivenWhenThenFixture(User.class);
		this.fixture.registerAnnotatedCommandHandler(new UserCommandHandler(this.fixture.getRepository()));
		UserHolder.set(USER_AUTH);
	}
	@After
	public void teardown() {
		UserHolder.clear();
	}

	@Test
	public void testAddUser() {
		this.fixture.givenNoPriorActivity()
			.when(ADD_USER_COMMAND)
			.expectEvents(UserAddedEvent.fromCommand(ADD_USER_COMMAND));
	}

	@Test
	public void testAddFavorite() {
		final Set<LocationId> expectedLocations = asSet(ADD_FAV_LOCATION_COMMAND.locationId);

		this.fixture.givenCommands(ADD_USER_COMMAND)
			.when(ADD_FAV_LOCATION_COMMAND)
			.expectEvents(new FavoriteLocationsUpdatedEvent(USER_ID, expectedLocations));
	}

	@Test
	public void testAddFavorite_idempotent() {
		this.fixture.givenCommands(ADD_USER_COMMAND, ADD_FAV_LOCATION_COMMAND)
			.when(ADD_FAV_LOCATION_COMMAND)
			.expectEvents();
	}

	@Test
	public void testRemoveFavorite() {
		final Set<LocationId> expectedLocations = asSet();

		this.fixture.givenCommands(ADD_USER_COMMAND, ADD_FAV_LOCATION_COMMAND)
			.when(REMOVE_FAV_LOCATION_COMMAND)
			.expectEvents(new FavoriteLocationsUpdatedEvent(USER_ID, expectedLocations));
	}
	@Test
	public void testRemoveFavorite_idempotent() {
		this.fixture.givenCommands(ADD_USER_COMMAND, ADD_FAV_LOCATION_COMMAND, REMOVE_FAV_LOCATION_COMMAND)
			.when(REMOVE_FAV_LOCATION_COMMAND)
			.expectEvents();
	}
	@Test
	public void testRemoveFavorite_idempotentEarly() {
		this.fixture.givenCommands(ADD_USER_COMMAND)
			.when(REMOVE_FAV_LOCATION_COMMAND)
			.expectEvents();
	}
	@Test
	public void testRemoveFavorite_onlyAffectsCorrectLocation() {
		final Set<LocationId> expectedLocations = asSet(ADD_FAV_LOCATION_COMMAND_2.locationId);

		this.fixture.givenCommands(ADD_USER_COMMAND, ADD_FAV_LOCATION_COMMAND, ADD_FAV_LOCATION_COMMAND_2)
			.when(REMOVE_FAV_LOCATION_COMMAND)
			.expectEvents(new FavoriteLocationsUpdatedEvent(USER_ID, expectedLocations));
	}


	@Test
	public void testAddRole() {
		this.fixture.givenCommands(ADD_USER_COMMAND)
			.when(ADD_ROLE_TO_USER_COMMAND)
			.expectEvents(new UserRolesUpdatedEvent(USER_ID, asSet(ROLE)));
	}
	@Test
	public void testAddRole_idempotent() {
		this.fixture.givenCommands(ADD_USER_COMMAND, ADD_ROLE_TO_USER_COMMAND)
			.when(ADD_ROLE_TO_USER_COMMAND)
			.expectEvents();
	}

	@Test
	public void testRemoveRole() {
		this.fixture.givenCommands(ADD_USER_COMMAND, ADD_ROLE_TO_USER_COMMAND)
		.when(REMOVE_ROLE_FROM_USER_COMMAND)
		.expectEvents(new UserRolesUpdatedEvent(USER_ID, asSet()));
	}
	@Test
	public void testRemoveRole_idempotent() {
		this.fixture.givenCommands(ADD_USER_COMMAND, ADD_ROLE_TO_USER_COMMAND, REMOVE_ROLE_FROM_USER_COMMAND)
		.when(REMOVE_ROLE_FROM_USER_COMMAND)
		.expectEvents();
	}
	@Test
	public void testRemoveRole_idempotentEarly() {
		this.fixture.givenCommands(ADD_USER_COMMAND)
		.when(REMOVE_ROLE_FROM_USER_COMMAND)
		.expectEvents();
	}
	@Test
	public void testRemoveRole_correctRole() {
		this.fixture.givenCommands(ADD_USER_COMMAND, ADD_ROLE_TO_USER_COMMAND, ADD_ROLE_TO_USER_COMMAND_2)
		.when(REMOVE_ROLE_FROM_USER_COMMAND)
		.expectEvents(new UserRolesUpdatedEvent(USER_ID, asSet(ROLE_2)));
	}


	@SafeVarargs
	private static <T> Set<T> asSet(T... locations) {
		return new HashSet<>(asList(locations));
	}

}
