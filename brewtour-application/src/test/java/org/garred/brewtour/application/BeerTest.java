package org.garred.brewtour.application;

import static org.garred.brewtour.domain.ReviewMedal.GOLD;
import static org.garred.brewtour.domain.ReviewMedal.SILVER;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.garred.brewtour.application.command.beer.AddBeerCommand;
import org.garred.brewtour.application.command.beer.AddBeerReviewCommand;
import org.garred.brewtour.application.command.beer.ModifyBeerCommand;
import org.garred.brewtour.application.command.location.AddLocationCommand;
import org.garred.brewtour.application.command.location.AddLocationCommentCommand;
import org.garred.brewtour.application.command.location.AddLocationReviewCommand;
import org.garred.brewtour.application.command.location.BeerAvailableCommand;
import org.garred.brewtour.application.command.location.BeerUnavailableCommand;
import org.garred.brewtour.application.command.location.UpdateLocationDescriptionCommand;
import org.garred.brewtour.application.event.beer.BeerAddedEvent;
import org.garred.brewtour.application.event.beer.BeerModifiedEvent;
import org.garred.brewtour.application.event.beer.BeerRatingUpdatedEvent;
import org.garred.brewtour.application.event.beer.BeerReviewAddedByAnonymousEvent;
import org.garred.brewtour.application.event.beer.BeerReviewAddedByUserEvent;
import org.garred.brewtour.domain.AvailableImages;
import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.BreweryId;
import org.garred.brewtour.domain.Image;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.security.GuestUserAuth;
import org.garred.brewtour.security.UserHolder;
import org.garred.brewtour.service.BeerCommandHandlerService;
import org.garred.brewtour.view.UserAuthView;
import org.junit.Before;
import org.junit.Test;

public class BeerTest {

	private static final BigDecimal LONGITUDE = new BigDecimal("-122.315");
	private static final BigDecimal LATITUDE = new BigDecimal("47.614");
	private static final LocationId LOCATION_ID = new LocationId("LOCA10001");
	private static final BeerId BEER_ID = new BeerId("BEER10101");
	protected static final String BREWERY_NAME = "Stone Brewing";
	protected static final BreweryId BREWERY_ID = new BreweryId("BREW10001");

	private static final AddLocationCommentCommand ADD_LOCATION_COMMENT_COMMAND = new AddLocationCommentCommand(LOCATION_ID, "This place closes early on Mondays");
	private static final String LOCATION_DESCRIPTION = "some interesting description";
	private static final String LOCATION_DESCRIPTION_2 = "a less interesting description of some (or another) location";
	private static final UpdateLocationDescriptionCommand UPDATE_LOCATION_DESCRIPTION = new UpdateLocationDescriptionCommand(LOCATION_ID, LOCATION_DESCRIPTION_2);
	private static final Image IMAGE_1 = new Image("image 1");
	private static final Image IMAGE_2 = new Image("image 2");
	private static final AvailableImages AVAILABLE_IMAGES = new AvailableImages(IMAGE_1, IMAGE_2, null);
	private static final String BEER_NAME = "A beer name";
	private static final BeerAvailableCommand BEER_AVAILABLE_COMMAND = new BeerAvailableCommand(LOCATION_ID, BEER_ID);
	private static final BeerUnavailableCommand BEER_UNAVAILABLE_COMMAND = new BeerUnavailableCommand(LOCATION_ID, BEER_ID);
	private static final String STYLE = "Beer Style";
	private static final String STYLE_2 = "another beer Style";
	private static final String CATEGORY = "beer category";
	private static final String CATEGORY_2 = "another beer category";
	private static final BigDecimal ABV = new BigDecimal("6.3");
	private static final BigDecimal ABV_2 = new BigDecimal("5.1");
	private static final BigDecimal IBU = new BigDecimal("76");
	private static final BigDecimal IBU_2 = new BigDecimal("29");
	protected static final UserId USER_ID = new UserId("a user id");
	private static final String SCREEN_NAME = "User ScreenName";
	protected static final LocalDateTime DATE_TIME = LocalDateTime.of(2015, 9, 20, 8, 50);
	private static final String LOCATION_REVIEW = "some review";
	private static final String BEER_REVIEW = "spicy but with a full body";
	private static final AddBeerReviewCommand ADD_BEER_REVIEW_COMMAND = new AddBeerReviewCommand(BEER_ID, SILVER, BEER_REVIEW);

	private static final AddLocationReviewCommand ADD_LOCATION_REVIEW_COMMAND = new AddLocationReviewCommand(LOCATION_ID, GOLD, LOCATION_REVIEW);

	private static final ModifyBeerCommand MODIFY_BEER_COMMAND = new ModifyBeerCommand(BEER_ID, STYLE_2, CATEGORY_2, ABV_2, IBU_2);
	private static final AddLocationCommand ADD_LOCATION_COMMAND = new AddLocationCommand("a location name");
	private static final AddBeerCommand ADD_BEER = new AddBeerCommand(BEER_NAME, BREWERY_ID, BREWERY_NAME, STYLE, CATEGORY, ABV, IBU);


	private FixtureConfiguration<Beer> fixture;

	@Before
	public void setup() {
		this.fixture = Fixtures.newGivenWhenThenFixture(Beer.class);
		this.fixture.registerAnnotatedCommandHandler(new BeerCommandHandler(this.fixture.getRepository(), new BeerCommandHandlerService() {
			BeerIdentifierFactoryStub identifierStub = new BeerIdentifierFactoryStub();
			@Override
			public LocalDateTime now() {
				return DATE_TIME;
			}
			@Override
			public BeerId nextBeerId() {
				return this.identifierStub.next();
			}
		}));
		UserHolder.set(new GuestUserAuth(USER_ID));
	}


	@Test
	public void testAddBeer() {
		this.fixture.givenNoPriorActivity()
			.when(ADD_BEER)
			.expectEvents(BeerAddedEvent.fromCommand(ADD_BEER, BEER_ID));
	}

	@Test
	public void testModifyBeer() {
		this.fixture.givenCommands(ADD_BEER)
			.when(MODIFY_BEER_COMMAND)
			.expectEvents(BeerModifiedEvent.fromCommand(MODIFY_BEER_COMMAND));
	}


	@Test
	public void testAddBeerReview_anonymous() {
		this.fixture.givenCommands(ADD_BEER)
			.when(ADD_BEER_REVIEW_COMMAND)
			.expectEvents(
				BeerReviewAddedByAnonymousEvent.fromCommand(ADD_BEER_REVIEW_COMMAND, USER_ID, DATE_TIME),
				new BeerRatingUpdatedEvent(BEER_ID, SILVER)
				);
	}
	@Test
	public void testAddBeerReview_byUser() {
		UserHolder.set(userAuth(USER_ID));
		this.fixture.givenCommands(ADD_BEER)
		.when(ADD_BEER_REVIEW_COMMAND)
		.expectEvents(
				BeerReviewAddedByUserEvent.fromCommand(ADD_BEER_REVIEW_COMMAND, USER_ID, SCREEN_NAME, DATE_TIME),
				new BeerRatingUpdatedEvent(BEER_ID, SILVER)
				);
	}


	private static UserAuthView userAuth(UserId userId) {
		final UserAuthView auth = new UserAuthView();
		auth.userId = userId;
		auth.screenName = SCREEN_NAME;
		return auth;
	}
}