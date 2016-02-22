package org.garred.brewtour.infrastructure;

import static org.garred.brewtour.domain.ReviewMedal.SILVER;
import static org.garred.brewtour.security.SystemUserAuth.SYSTEM;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.garred.brewtour.application.command.location.AddLocationReviewCommand;
import org.garred.brewtour.application.command.user.AddUserCommand;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.security.GuestUserAuth;
import org.garred.brewtour.security.UserAuthorizationException;
import org.garred.brewtour.security.UserHolder;
import org.garred.brewtour.security.UserNotLoggedInException;
import org.junit.Before;
import org.junit.Test;

public class BeertourCommandDispatchInterceptorTest {

	private static final UserId USER_ID = new UserId("userId");
	private static final GuestUserAuth GUEST_USER = new GuestUserAuth(USER_ID);
	private static final CommandMessage<AddUserCommand> ADMIN_COMMAND_MESSAGE = new GenericCommandMessage<AddUserCommand>(new AddUserCommand("screen name", "login", "password"));
	private static final CommandMessage<AddLocationReviewCommand> NORMAL_COMMAND_MESSAGE = new GenericCommandMessage<AddLocationReviewCommand>(new AddLocationReviewCommand(new LocationId("locationId"), SILVER, "Some review"));

	private final BeertourCommandDispatchInterceptor interceptor = new BeertourCommandDispatchInterceptor();

	@Before
	public void setup() {
		UserHolder.clear();
	}

	@Test(expected = UserNotLoggedInException.class)
	public void testNoUser() {
		this.interceptor.handle(NORMAL_COMMAND_MESSAGE);
	}

	@Test
	public void testNormalCommand() {
		UserHolder.set(GUEST_USER);
		this.interceptor.handle(NORMAL_COMMAND_MESSAGE);
	}

	@Test(expected = UserAuthorizationException.class)
	public void testNotAuthorizedUser() {
		UserHolder.set(GUEST_USER);
		this.interceptor.handle(ADMIN_COMMAND_MESSAGE);
	}

	@Test
	public void testAdminCommand() {
		UserHolder.set(SYSTEM);
		this.interceptor.handle(ADMIN_COMMAND_MESSAGE);
	}
}
