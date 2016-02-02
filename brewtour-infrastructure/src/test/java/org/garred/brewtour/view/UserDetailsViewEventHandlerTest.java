package org.garred.brewtour.view;

import static org.garred.brewtour.domain.Hash.hashFromPassword;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.garred.brewtour.application.event.location.user_fired.BeerReviewAddedByUserEvent;
import org.garred.brewtour.application.event.location.user_fired.BeerStarRatingAddedByUserEvent;
import org.garred.brewtour.application.event.location.user_fired.LocationReviewAddedByUserEvent;
import org.garred.brewtour.application.event.location.user_fired.LocationStarRatingAddedByUserEvent;
import org.garred.brewtour.application.event.user.UserAddedEvent;
import org.garred.brewtour.domain.Hash;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.service.UserDetailsViewRepositoryStub;
import org.junit.Before;
import org.junit.Test;

public class UserDetailsViewEventHandlerTest {

	private static final UserId USER_ID = new UserId("a user id");
	private static final String USER_NAME = "userName";
	private static final Hash USER_HASH = hashFromPassword(USER_ID, "password");

	private static final String BEER_NAME = "Some Iconic Stout";
	private static final LocationId LOCATION_ID = new LocationId("LOCA10001");

	private static final LocalDateTime DATE_TIME = LocalDateTime.of(2015, 9, 20, 8, 50);

	private static final int LOCATION_STARS = 4;
	private static final int BEER_STARS = 3;
	private static final String LOCATION_REVIEW = "some review";
	private static final String BEER_REVIEW = "spicy but with a full body";


	private UserDetailsViewRepositoryStub repo;
	private UserDetailsViewEventHandler eventHandler;

	@Before
	public void setup() {
		this.repo = new UserDetailsViewRepositoryStub();
		this.eventHandler = new UserDetailsViewEventHandler(this.repo);

		this.repo.save(defaultDetailsView());
	}


	@Test
	public void testAdd() {
		this.repo.objectMap().clear();
		assertTrue(this.repo.objectMap().isEmpty());

		this.eventHandler.on(new UserAddedEvent(USER_ID, USER_NAME, USER_HASH));
		assertEquals(defaultDetailsView(), this.repo.get(USER_ID));
	}

	@Test
	public void testLocationReviewAdded() {
		final UserDetailsView expected = defaultDetailsView();
		expected.locationReviews.put(LOCATION_ID, new Review(LOCATION_STARS, LOCATION_REVIEW));

		this.eventHandler.on(new LocationReviewAddedByUserEvent(LOCATION_ID, USER_ID, DATE_TIME, LOCATION_STARS, LOCATION_REVIEW));
		assertEquals(expected, this.repo.get(USER_ID));
	}
	@Test
	public void testLocationStarRatingAdded() {
		final UserDetailsView expected = defaultDetailsView();
		expected.locationReviews.put(LOCATION_ID, new Review(LOCATION_STARS, null));

		this.eventHandler.on(new LocationStarRatingAddedByUserEvent(LOCATION_ID, USER_ID, LOCATION_STARS));
		assertEquals(expected, this.repo.get(USER_ID));
	}

	@Test
	public void testBeerReviewAdded() {
		final UserDetailsView expected = defaultDetailsView();
		final Map<String,Review> reviewMap = new HashMap<>();
		reviewMap.put(BEER_NAME, new Review(BEER_STARS, BEER_REVIEW));
		expected.beerReviews.put(LOCATION_ID, reviewMap);

		this.eventHandler.on(new BeerReviewAddedByUserEvent(LOCATION_ID, USER_ID, BEER_NAME, BEER_STARS, DATE_TIME, BEER_REVIEW));
		assertEquals(expected, this.repo.get(USER_ID));
	}
	@Test
	public void testBeerStarRatingAdded() {
		final UserDetailsView expected = defaultDetailsView();
		final Map<String,Review> reviewMap = new HashMap<>();
		reviewMap.put(BEER_NAME, new Review(BEER_STARS, null));
		expected.beerReviews.put(LOCATION_ID, reviewMap);

		this.eventHandler.on(new BeerStarRatingAddedByUserEvent(LOCATION_ID, USER_ID, BEER_NAME, BEER_STARS));
		assertEquals(expected, this.repo.get(USER_ID));
	}

	private static UserDetailsView defaultDetailsView() {
		final UserDetailsView userDetailsView = new UserDetailsView();
		userDetailsView.userId = USER_ID;
		userDetailsView.login = USER_NAME;
		userDetailsView.locationReviews = new HashMap<>();
		userDetailsView.beerReviews = new HashMap<>();
		return userDetailsView;
	}
}
