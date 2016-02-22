package org.garred.brewtour.api;

import static org.garred.brewtour.AbstractSerializationTest.TestType.COMMAND;

import org.garred.brewtour.AbstractSerializationTest;
import org.garred.brewtour.application.command.user.AddFavoriteBeerCommand;
import org.garred.brewtour.application.command.user.AddFavoriteLocationCommand;
import org.garred.brewtour.application.command.user.AddRoleToUserCommand;
import org.garred.brewtour.application.command.user.AddUserCommand;
import org.garred.brewtour.application.command.user.RemoveFavoriteBeerCommand;
import org.garred.brewtour.application.command.user.RemoveFavoriteLocationCommand;
import org.garred.brewtour.application.command.user.RemoveRoleFromUserCommand;
import org.garred.brewtour.domain.UserId;
import org.junit.Test;

public class UserCommandSerializationTest extends AbstractSerializationTest {

	private static final UserId USER_ID_2 = new UserId("a user id");

	public UserCommandSerializationTest() {
		super("command/user", "org.garred.brewtour.application.command.user", COMMAND);
	}

	@Test
	public void testAddUser() {
		reflectionValidate(new AddUserCommand("A Screen name", "user_name0", "p@33w0rd"));
	}

	@Test
	public void testAddRoleToUser() {
		reflectionValidate(new AddRoleToUserCommand(USER_ID_2, "com.nullgeodesic.sec.TestRole"));
	}

	@Test
	public void testRemoveRoleFromUser() {
		reflectionValidate(new RemoveRoleFromUserCommand(USER_ID_2, "com.nullgeodesic.sec.TestRole"));
	}

	@Test
	public void testAddFavoriteLocation() {
		reflectionValidate(new AddFavoriteLocationCommand(LOCATION_ID));
	}
	@Test
	public void testRemoveFavoriteLocation() {
		reflectionValidate(new RemoveFavoriteLocationCommand(LOCATION_ID));
	}
	@Test
	public void testAddFavoriteBeer() {
		reflectionValidate(new AddFavoriteBeerCommand(BEER_ID));
	}
	@Test
	public void testRemoveFavoriteBeer() {
		reflectionValidate(new RemoveFavoriteBeerCommand(BEER_ID));
	}

}
