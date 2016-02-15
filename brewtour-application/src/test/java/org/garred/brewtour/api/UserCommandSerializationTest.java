package org.garred.brewtour.api;

import static org.garred.brewtour.AbstractSerializationTest.TestType.COMMAND;

import org.garred.brewtour.AbstractSerializationTest;
import org.garred.brewtour.application.command.user.AddRoleToUserCommand;
import org.garred.brewtour.application.command.user.AddUserCommand;
import org.garred.brewtour.application.command.user.RemoveRoleFromUserCommand;
import org.garred.brewtour.domain.UserId;
import org.junit.Test;

public class UserCommandSerializationTest extends AbstractSerializationTest {

	public UserCommandSerializationTest() {
		super("command/user", "org.garred.brewtour.application.command.user", COMMAND);
	}

	@Test
	public void testAddUser() {
		reflectionValidate(new AddUserCommand("A Screen name", "user_name0", "p@33w0rd"));
	}

	@Test
	public void testAddRoleToUser() {
		reflectionValidate(new AddRoleToUserCommand(new UserId("a user id"), "com.nullgeodesic.sec.TestRole"));
	}

	@Test
	public void testRemoveRoleFromUser() {
		reflectionValidate(new RemoveRoleFromUserCommand(new UserId("a user id"), "com.nullgeodesic.sec.TestRole"));
	}


}
