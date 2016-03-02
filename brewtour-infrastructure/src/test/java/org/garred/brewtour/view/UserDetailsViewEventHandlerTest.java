package org.garred.brewtour.view;

import static org.garred.brewtour.domain.Hash.hashFromPassword;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.Hash;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.ReviewMedal;
import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.domain.event.beer.BeerReviewAddedByUserEvent;
import org.garred.brewtour.domain.event.beer.BeerStarRatingAddedByUserEvent;
import org.garred.brewtour.domain.event.location.user_fired.LocationReviewAddedByUserEvent;
import org.garred.brewtour.domain.event.location.user_fired.LocationStarRatingAddedByUserEvent;
import org.garred.brewtour.domain.event.user.UserAddedEvent;
import org.garred.brewtour.service.UserDetailsViewRepositoryStub;
import org.junit.Before;
import org.junit.Test;

public class UserDetailsViewEventHandlerTest {

	private static final BeerId BEER_ID = new BeerId("aBeerId");
	private static final UserId USER_ID = new UserId("a user id");
	private static final String SCREEN_NAME = "screen name";
	private static final String USER_NAME = "userName";
	private static final Hash USER_HASH = hashFromPassword(USER_ID, "password");

	private static final String BEER_NAME = "Some Iconic Stout";
	private static final LocationId LOCATION_ID = new LocationId("LOCA10001");

	private static final LocalDateTime DATE_TIME = LocalDateTime.of(2015, 9, 20, 8, 50);

	private static final ReviewMedal LOCATION_MEDAL = ReviewMedal.GOLD;
	private static final ReviewMedal BEER_MEDAL = ReviewMedal.SILVER;
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

		this.eventHandler.on(new UserAddedEvent(USER_ID, SCREEN_NAME, USER_NAME, USER_HASH));
		assertEquals(defaultDetailsView(), this.repo.get(USER_ID));
	}

	@Test
	public void testLocationReviewAdded() {
		final UserDetailsView expected = defaultDetailsView();
		expected.locationReviews.put(LOCATION_ID, new Review(SCREEN_NAME, LOCATION_MEDAL.name(), LOCATION_REVIEW));

		this.eventHandler.on(new LocationReviewAddedByUserEvent(LOCATION_ID, USER_ID, SCREEN_NAME, DATE_TIME, LOCATION_MEDAL, LOCATION_REVIEW));
		assertEquals(expected, this.repo.get(USER_ID));
	}
	@Test
	public void testLocationStarRatingAdded() {
		final UserDetailsView expected = defaultDetailsView();
		expected.locationReviews.put(LOCATION_ID, new Review(SCREEN_NAME, LOCATION_MEDAL.name(), null));

		this.eventHandler.on(new LocationStarRatingAddedByUserEvent(LOCATION_ID, USER_ID, SCREEN_NAME, LOCATION_MEDAL));
		assertEquals(expected, this.repo.get(USER_ID));
	}

	@Test
	public void testBeerReviewAdded() {
		final UserDetailsView expected = defaultDetailsView();
		expected.beerReviews.put(BEER_ID, new Review(SCREEN_NAME, BEER_MEDAL.name(), BEER_REVIEW));

		this.eventHandler.on(new BeerReviewAddedByUserEvent(BEER_ID, USER_ID, SCREEN_NAME, BEER_MEDAL, DATE_TIME, BEER_REVIEW));
		assertEquals(expected, this.repo.get(USER_ID));
	}
	@Test
	public void testBeerStarRatingAdded() {
		final UserDetailsView expected = defaultDetailsView();
		expected.beerReviews.put(BEER_ID, new Review(SCREEN_NAME, BEER_MEDAL.name(), null));

		this.eventHandler.on(new BeerStarRatingAddedByUserEvent(BEER_ID, USER_ID, SCREEN_NAME, BEER_MEDAL));
		assertEquals(expected, this.repo.get(USER_ID));
	}

	private static UserDetailsView defaultDetailsView() {
		final UserDetailsView userDetailsView = new UserDetailsView();
		userDetailsView.userId = USER_ID;
		userDetailsView.screenName = SCREEN_NAME;
		userDetailsView.login = USER_NAME;
		userDetailsView.locationReviews = new HashMap<>();
		userDetailsView.beerReviews = new HashMap<>();
		return userDetailsView;
	}
}
