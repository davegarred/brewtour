package org.garred.brewtour.application;

import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;

import java.util.HashSet;
import java.util.Set;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.domain.aggregates.User;
import org.garred.brewtour.domain.aggregates.UserCommandHandler;
import org.garred.brewtour.domain.command.user.AddFavoriteBeerCommand;
import org.garred.brewtour.domain.command.user.AddFavoriteLocationCommand;
import org.garred.brewtour.domain.command.user.AddRoleToUserCommand;
import org.garred.brewtour.domain.command.user.AddUserCommand;
import org.garred.brewtour.domain.command.user.RemoveFavoriteBeerCommand;
import org.garred.brewtour.domain.command.user.RemoveFavoriteLocationCommand;
import org.garred.brewtour.domain.command.user.RemoveRoleFromUserCommand;
import org.garred.brewtour.domain.event.user.FavoriteBeersUpdatedEvent;
import org.garred.brewtour.domain.event.user.FavoriteLocationsUpdatedEvent;
import org.garred.brewtour.domain.event.user.UserAddedEvent;
import org.garred.brewtour.domain.event.user.UserRolesUpdatedEvent;
import org.garred.brewtour.security.UserAuth;
import org.garred.brewtour.security.UserHolder;
import org.garred.brewtour.service.UserCommandHandlerService;
import org.garred.brewtour.view.UserAuthView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserTest {

	private static final BeerId BEER_ID = new BeerId("beerId");
	private static final LocationId LOCATION_ID = new LocationId("locationId");
	private static final String ROLE = "org.garred.brewtour.SomeRole";
	private static final String ROLE_2 = "org.garred.brewtour.SomeOtherRole";
	private static final String SCREEN_NAME = "a user screen name";
	private static final String LOGIN = "dave@gmail.com";
	private static final String PASSWORD = "password";
	private static final UserId USER_ID = new UserId("a user id");
	private static final RemoveRoleFromUserCommand REMOVE_ROLE_FROM_USER_COMMAND = new RemoveRoleFromUserCommand(USER_ID, ROLE);
	private static final AddRoleToUserCommand ADD_ROLE_TO_USER_COMMAND = new AddRoleToUserCommand(USER_ID, ROLE);
	private static final AddRoleToUserCommand ADD_ROLE_TO_USER_COMMAND_2 = new AddRoleToUserCommand(USER_ID, ROLE_2);
	private static final UserAuth USER_AUTH = user(USER_ID);

	private static final AddUserCommand ADD_USER_COMMAND = new AddUserCommand(SCREEN_NAME, LOGIN, PASSWORD);

	private FixtureConfiguration<User> fixture;

	@Before
	public void setup() {
		this.fixture = Fixtures.newGivenWhenThenFixture(User.class);
		this.fixture.registerAnnotatedCommandHandler(new UserCommandHandler(this.fixture.getRepository(), new UserCommandHandlerService() {
			@Override
			public UserId randomUserId() {
				return USER_ID;
			}
			@Override
			public UserId lastUserId() {
				return null;
			}
		}));
		UserHolder.set(USER_AUTH);
	}
	private static UserAuth user(UserId userId) {
		final UserAuthView view = new UserAuthView();
		view.userId = userId;
		return view;
	}
	@After
	public void teardown() {
		UserHolder.clear();
	}

	@Test
	public void testAddUser() {
		this.fixture.givenNoPriorActivity()
			.when(ADD_USER_COMMAND)
			.expectEvents(UserAddedEvent.fromCommand(ADD_USER_COMMAND, USER_ID));
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

	@Test
	public void testAddFavoriteLocation() {
		this.fixture.givenCommands(ADD_USER_COMMAND)
			.when(new AddFavoriteLocationCommand(LOCATION_ID))
			.expectEvents(new FavoriteLocationsUpdatedEvent(USER_ID, new HashSet<>(asList(LOCATION_ID))));
	}
	@Test
	public void testAddFavoriteLocation_idempotent() {
		this.fixture.givenCommands(ADD_USER_COMMAND, new AddFavoriteLocationCommand(LOCATION_ID))
		.when(new AddFavoriteLocationCommand(LOCATION_ID))
		.expectEvents();
	}
	@Test
	public void testRemoveFavoriteLocation() {
		this.fixture.givenCommands(ADD_USER_COMMAND, new AddFavoriteLocationCommand(LOCATION_ID))
		.when(new RemoveFavoriteLocationCommand(LOCATION_ID))
		.expectEvents(new FavoriteLocationsUpdatedEvent(USER_ID, emptySet()));
	}
	@Test
	public void testRemoveFavoriteLocation_idempotent() {
		this.fixture.givenCommands(ADD_USER_COMMAND, new RemoveFavoriteLocationCommand(LOCATION_ID))
		.when(new RemoveFavoriteLocationCommand(LOCATION_ID))
		.expectEvents();
	}
	@Test
	public void testAddFavoriteBeer() {
		this.fixture.givenCommands(ADD_USER_COMMAND)
		.when(new AddFavoriteBeerCommand(BEER_ID))
		.expectEvents(new FavoriteBeersUpdatedEvent(USER_ID, new HashSet<>(asList(BEER_ID))));
	}
	@Test
	public void testAddFavoriteBeer_idempotent() {
		this.fixture.givenCommands(ADD_USER_COMMAND, new AddFavoriteBeerCommand(BEER_ID))
		.when(new AddFavoriteBeerCommand(BEER_ID))
		.expectEvents();
	}
	@Test
	public void testRemoveFavoriteBeer() {
		this.fixture.givenCommands(ADD_USER_COMMAND, new AddFavoriteBeerCommand(BEER_ID))
		.when(new RemoveFavoriteBeerCommand(BEER_ID))
		.expectEvents(new FavoriteBeersUpdatedEvent(USER_ID, emptySet()));
	}
	@Test
	public void testRemoveFavoriteBeer_idempotent() {
		this.fixture.givenCommands(ADD_USER_COMMAND, new RemoveFavoriteBeerCommand(BEER_ID))
		.when(new RemoveFavoriteBeerCommand(BEER_ID))
		.expectEvents();
	}

	@SafeVarargs
	private static <T> Set<T> asSet(T... locations) {
		return new HashSet<>(asList(locations));
	}

}
