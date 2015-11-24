package org.garred.brewtour.service;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.garred.brewtour.api.AddFavoriteLocation;
import org.garred.brewtour.api.RemoveFavoriteLocation;
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

	private UserServiceImpl userService;

	@Before
	public void setup() {
		this.userRepo = new UserDetailsRepositoryStub();
		this.userService = new UserServiceImpl(this.userRepo);
	}

	@Test
	public void testAddFavorites() {
		this.userService.addFavorite(USER_ID, new AddFavoriteLocation(LOCATION_ID));
		final UserDetails user = this.userService.getDetails(USER_ID);
		assertSingleItemInCollection(LOCATION_ID, user.getFavoriteLocations());
	}

	@Test
	public void testAddFavorites_sameLocation() {
		this.userService.addFavorite(USER_ID, new AddFavoriteLocation(LOCATION_ID));
		this.userService.addFavorite(USER_ID, new AddFavoriteLocation(LOCATION_ID));
		final UserDetails user = this.userService.getDetails(USER_ID);
		assertSingleItemInCollection(LOCATION_ID, user.getFavoriteLocations());
	}

	@Test
	public void testDeleteFavorites() {
		this.userService.addFavorite(USER_ID, new AddFavoriteLocation(LOCATION_ID));
		this.userService.addFavorite(USER_ID, new AddFavoriteLocation(LOCATION_ID_2));
		this.userService.removeFavorite(USER_ID, new RemoveFavoriteLocation(LOCATION_ID));
		final UserDetails user = this.userService.getDetails(USER_ID);
		assertSingleItemInCollection(LOCATION_ID_2, user.getFavoriteLocations());
	}

	private static <T> void assertSingleItemInCollection(T object, Collection<T> collection) {
		assertEquals(1, collection.size());
		assertEquals(object, collection.iterator().next());
	}
}
