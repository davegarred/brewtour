package org.garred.brewtour.application;

import static java.util.Arrays.asList;

import java.util.HashSet;
import java.util.Set;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.garred.brewtour.application.command.user.AddRoleToUserCommand;
import org.garred.brewtour.application.command.user.AddUserCommand;
import org.garred.brewtour.application.command.user.RemoveRoleFromUserCommand;
import org.garred.brewtour.application.event.user.UserAddedEvent;
import org.garred.brewtour.application.event.user.UserRolesUpdatedEvent;
import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.security.GuestUserAuth;
import org.garred.brewtour.security.UserAuth;
import org.garred.brewtour.security.UserHolder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserTest {

	private static final String ROLE = "org.garred.brewtour.SomeRole";
	private static final String ROLE_2 = "org.garred.brewtour.SomeOtherRole";
	private static final String LOGIN = "a user login";
	private static final String PASSWORD = "password";
	private static final UserId USER_ID = new UserId("a user id");
	private static final RemoveRoleFromUserCommand REMOVE_ROLE_FROM_USER_COMMAND = new RemoveRoleFromUserCommand(USER_ID, ROLE);
	private static final AddRoleToUserCommand ADD_ROLE_TO_USER_COMMAND = new AddRoleToUserCommand(USER_ID, ROLE);
	private static final AddRoleToUserCommand ADD_ROLE_TO_USER_COMMAND_2 = new AddRoleToUserCommand(USER_ID, ROLE_2);
	private static final UserAuth USER_AUTH = new GuestUserAuth(USER_ID);

	private static final AddUserCommand ADD_USER_COMMAND = new AddUserCommand(USER_ID, LOGIN, PASSWORD);

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
