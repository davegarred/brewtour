package org.garred.brewtour.integration;

import static java.util.Arrays.asList;
import static org.garred.brewtour.domain.LocaleId.SEATTLE;
import static org.garred.brewtour.domain.ReviewMedal.GOLD;
import static org.garred.brewtour.domain.ReviewMedal.SILVER;
import static org.garred.brewtour.security.SystemUserAuth.SYSTEM;
import static org.garred.brewtour.view.BeerView.newBeerView;
import static org.garred.brewtour.view.UserAuthView.ADMIN_ROLE;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.garred.brewtour.application.IdentifierFactory;
import org.garred.brewtour.application.command.beer.AddBeerCommand;
import org.garred.brewtour.application.command.beer.AddBeerReviewCommand;
import org.garred.brewtour.application.command.location.AddLocationCommand;
import org.garred.brewtour.application.command.location.AddLocationCommentCommand;
import org.garred.brewtour.application.command.location.AddLocationReviewCommand;
import org.garred.brewtour.application.command.location.BeerAvailableCommand;
import org.garred.brewtour.application.command.user.AddUserCommand;
import org.garred.brewtour.domain.AvailableImages;
import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.Image;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.repository.AdminViewRepository;
import org.garred.brewtour.repository.BeerViewRepository;
import org.garred.brewtour.repository.LocaleViewRepository;
import org.garred.brewtour.repository.LocationViewRepository;
import org.garred.brewtour.repository.UserDetailsViewRepository;
import org.garred.brewtour.security.UserHolder;
import org.garred.brewtour.service.UserCommandHandlerService;
import org.garred.brewtour.view.AdminView;
import org.garred.brewtour.view.BeerView;
import org.garred.brewtour.view.LocaleView;
import org.garred.brewtour.view.LocationView;
import org.garred.brewtour.view.Review;
import org.garred.brewtour.view.UserAuthView;
import org.garred.brewtour.view.UserDetailsView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/persistence-config.xml",
		"classpath:spring/beans-config.xml",
		"classpath:spring/axon-config.xml"
})
public class IntegrationTest {

	private static final String BREWERY_NAME = "Stone Brewing";
	private static final String LOCATION_COMMENT = "Some very important comment on this location";
	private static final String LOCATION_NAME = "a location name";
	private static final BigDecimal LONGITUDE = new BigDecimal("-122.315");
	private static final BigDecimal LATITUDE = new BigDecimal("47.614");
	private static final String LOCATION_DESCRIPTION = "some interesting description";
	private static final String LOCATION_DESCRIPTION_2 = "a less interesting description of some (or another) location";
	private static final Image IMAGE_1 = new Image("image 1");
	private static final Image IMAGE_2 = new Image("image 2");
	private static final AvailableImages AVAILABLE_IMAGES = new AvailableImages(IMAGE_1, IMAGE_2, null);
	private static final String BEER_NAME = "A beer name";
	private static final String BEER_DESCRIPTION = "a very tasty beer";
	private static final String STYLE = "Beer Style";
	private static final String CATEGORY = "beer category";
	private static final BigDecimal ABV = new BigDecimal("6.3");
	private static final BigDecimal IBU = new BigDecimal("76");
	protected static final LocalDateTime DATE_TIME = LocalDateTime.of(2015, 9, 20, 8, 50);
	private static final String LOCATION_REVIEW = "some review";
	private static final String BEER_REVIEW = "spicy but with a full body";

	private static final String LOGIN = "a user login";
	private static final String PASSWORD = "password";
	private static final String SCREEN_NAME = "screen name";

	private static final AddUserCommand ADD_USER_COMMAND = new AddUserCommand(SCREEN_NAME, LOGIN, PASSWORD);

	private static final AddLocationCommand ADD_LOCATION_COMMAND = new AddLocationCommand(LOCATION_NAME);

	@Autowired
	private CommandGateway commandGateway;
	@Autowired
	private IdentifierFactory<LocationId> locationIdentifierFactory;
	@Autowired
	private IdentifierFactory<BeerId> beerIdentifierFactory;
	@Autowired
	private UserCommandHandlerService userCommandHandlerService;

	@Autowired
	private UserDetailsViewRepository userDetailsRepo;
	@Autowired
	private LocationViewRepository locationRepo;
	@Autowired
	private LocaleViewRepository localeRepo;
	@Autowired
	private BeerViewRepository beerRepo;
	@Autowired
	private AdminViewRepository adminRepo;

	@Test
	public void test() {
		UserHolder.set(SYSTEM);
		this.commandGateway.sendAndWait(ADD_USER_COMMAND);
		final UserId userId = this.userCommandHandlerService.lastUserId();
		setCurrentUser(userId, ADMIN_ROLE);
		UserDetailsView userDetails = this.userDetailsRepo.get(userId);
		assertEquals(LOGIN, userDetails.login);

		this.commandGateway.sendAndWait(ADD_LOCATION_COMMAND);
		final LocationId locationId = this.locationIdentifierFactory.last();

		LocationView location = this.locationRepo.get(locationId);
		assertEquals(LOCATION_NAME, location.name);
		final LocaleView locale = this.localeRepo.get(SEATTLE);
		assertEquals(SEATTLE.id, locale.name);

		this.commandGateway.sendAndWait(addBeerCommand(locationId));
		final BeerId beerId = this.beerIdentifierFactory.last();
		this.commandGateway.sendAndWait(new BeerAvailableCommand(locationId, beerId));
		location = this.locationRepo.get(locationId);
		assertSingleItemInCollection(beerId, location.beers);
		final BeerView beer = this.beerRepo.get(beerId);
		assertEquals(newBeerView(beerId, BEER_NAME, BREWERY_NAME), beer);

		setCurrentUser(userId);
		final Review expectedReview = new Review(SCREEN_NAME, GOLD.name(), LOCATION_REVIEW);
		this.commandGateway.sendAndWait(addLocationReviewCommand(locationId));
		location = this.locationRepo.get(locationId);
		assertEquals(GOLD.name(), location.medal);
		assertSingleItemInCollection(expectedReview, location.reviews);

		userDetails = this.userDetailsRepo.get(userId);
		assertSingleItemInCollection(expectedReview, userDetails.locationReviews.values());

		final Review expectedBeerReview = new Review(SCREEN_NAME, SILVER.name(), BEER_REVIEW);
		this.commandGateway.sendAndWait(addBeerReviewCommand(beerId));
		final BeerView reviewedBeer = this.beerRepo.get(beerId);
		assertEquals(SILVER.name(), reviewedBeer.medal);
		assertSingleItemInCollection(expectedBeerReview, reviewedBeer.userReviews);

		userDetails = this.userDetailsRepo.get(userId);
		assertEquals(expectedBeerReview, userDetails.beerReviews.get(beerId));

		this.commandGateway.sendAndWait(addLocationCommentCommand(locationId));
		final AdminView adminView = this.adminRepo.get(SEATTLE);
		assertEquals(1,adminView.comments.size());
		assertEquals(LOCATION_COMMENT, adminView.comments.get(0).comment);
	}

	public static void setCurrentUser(UserId userId, String... roles) {
		final UserAuthView view = new UserAuthView();
		view.userId = userId;
		view.screenName = SCREEN_NAME;
		view.roles = roles == null ? Collections.emptySet() : new HashSet<>(asList(roles));
		UserHolder.set(view);
	}

	private static AddBeerCommand addBeerCommand(LocationId locationId) {
		return new AddBeerCommand(BEER_NAME, locationId, BREWERY_NAME);
	}
	private static AddLocationReviewCommand addLocationReviewCommand(LocationId locationId) {
		return new AddLocationReviewCommand(locationId, GOLD, LOCATION_REVIEW);
	}
	private static AddBeerReviewCommand addBeerReviewCommand(BeerId beerId) {
		return new AddBeerReviewCommand(beerId, SILVER, BEER_REVIEW);
	}
	private static AddLocationCommentCommand addLocationCommentCommand(LocationId locationId) {
		return new AddLocationCommentCommand(locationId, LOCATION_COMMENT);
	}

	private static <T> void assertSingleItemInCollection(T expected, Collection<T> locationReviews) {
		assertEquals(1,locationReviews.size());
		assertEquals(expected, locationReviews.iterator().next());
	}
}
