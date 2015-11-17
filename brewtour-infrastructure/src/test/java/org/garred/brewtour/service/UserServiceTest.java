package org.garred.brewtour.service;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.garred.brewtour.application.LocationId;
import org.garred.brewtour.application.UserDetails;
import org.garred.brewtour.application.UserId;
import org.garred.brewtour.repository.UserDetailsRepository;
import org.junit.Before;
import org.junit.Test;

public class UserServiceTest {

	private static final UserId USER_ID = new UserId("a user id");
	private static final LocationId LOCATION_ID = new LocationId("a location id");
	private static final LocationId LOCATION_ID_2 = new LocationId("another location id");

	private UserDetailsRepository userRepo;
	
	private UserService userService;
	
	@Before
	public void setup() {
		userRepo = new UserDetailsRepositoryStub();
		userService = new UserService(userRepo);
	}
	
	@Test
	public void testAddFavorites() {
		userService.addFavorite(USER_ID, LOCATION_ID);
		UserDetails user = userService.getDetails(USER_ID);
		assertSingleItemInCollection(LOCATION_ID, user.getFavoriteLocations());
	}
	
	@Test
	public void testAddFavorites_sameLocation() {
		userService.addFavorite(USER_ID, LOCATION_ID);
		userService.addFavorite(USER_ID, LOCATION_ID);
		UserDetails user = userService.getDetails(USER_ID);
		assertSingleItemInCollection(LOCATION_ID, user.getFavoriteLocations());
	}

	@Test
	public void testDeleteFavorites() {
		userService.addFavorite(USER_ID, LOCATION_ID);
		userService.addFavorite(USER_ID, LOCATION_ID_2);
		userService.removeFavorite(USER_ID, LOCATION_ID);
		UserDetails user = userService.getDetails(USER_ID);
		assertSingleItemInCollection(LOCATION_ID_2, user.getFavoriteLocations());
	}
	
	private static <T> void assertSingleItemInCollection(T object, Collection<T> collection) {
		assertEquals(1, collection.size());
		assertEquals(object, collection.iterator().next());
	}
}
