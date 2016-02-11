package org.garred.brewtour.application;

import static org.garred.brewtour.domain.ReviewMedal.GOLD;
import static org.garred.brewtour.domain.ReviewMedal.SILVER;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.garred.brewtour.application.command.location.AddBeerCommand;
import org.garred.brewtour.application.command.location.AddBeerReviewCommand;
import org.garred.brewtour.application.command.location.AddLocationCommand;
import org.garred.brewtour.application.command.location.AddLocationCommentCommand;
import org.garred.brewtour.application.command.location.AddLocationReviewCommand;
import org.garred.brewtour.application.command.location.BeerAvailableCommand;
import org.garred.brewtour.application.command.location.BeerUnavailableCommand;
import org.garred.brewtour.application.command.location.ModifyBeerCommand;
import org.garred.brewtour.application.command.location.UpdateLocationAddressCommand;
import org.garred.brewtour.application.command.location.UpdateLocationDescriptionCommand;
import org.garred.brewtour.application.command.location.UpdateLocationHoursOfOperationCommand;
import org.garred.brewtour.application.command.location.UpdateLocationImagesCommand;
import org.garred.brewtour.application.command.location.UpdateLocationPhoneCommand;
import org.garred.brewtour.application.command.location.UpdateLocationPositionCommand;
import org.garred.brewtour.application.command.location.UpdateLocationWebsiteCommand;
import org.garred.brewtour.application.event.location.BeerAddedEvent;
import org.garred.brewtour.application.event.location.BeerAvailableEvent;
import org.garred.brewtour.application.event.location.BeerModifiedEvent;
import org.garred.brewtour.application.event.location.BeerUnavailableEvent;
import org.garred.brewtour.application.event.location.LocationAddedEvent;
import org.garred.brewtour.application.event.location.LocationAddressUpdatedEvent;
import org.garred.brewtour.application.event.location.LocationDescriptionUpdatedEvent;
import org.garred.brewtour.application.event.location.LocationHoursOfOperationUpdatedEvent;
import org.garred.brewtour.application.event.location.LocationImagesUpdatedEvent;
import org.garred.brewtour.application.event.location.LocationPhoneUpdatedEvent;
import org.garred.brewtour.application.event.location.LocationPositionUpdatedEvent;
import org.garred.brewtour.application.event.location.LocationWebsiteUpdatedEvent;
import org.garred.brewtour.application.event.location.user_fired.BeerRatingUpdatedEvent;
import org.garred.brewtour.application.event.location.user_fired.BeerReviewAddedByAnonymousEvent;
import org.garred.brewtour.application.event.location.user_fired.BeerReviewAddedByUserEvent;
import org.garred.brewtour.application.event.location.user_fired.LocationCommentAddedEvent;
import org.garred.brewtour.application.event.location.user_fired.LocationRatingUpdatedEvent;
import org.garred.brewtour.application.event.location.user_fired.LocationReviewAddedByAnonymousEvent;
import org.garred.brewtour.application.event.location.user_fired.LocationReviewAddedByUserEvent;
import org.garred.brewtour.domain.AvailableImages;
import org.garred.brewtour.domain.Image;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.security.GuestUserAuth;
import org.garred.brewtour.security.UserHolder;
import org.garred.brewtour.service.LocationCommandHandlerService;
import org.garred.brewtour.view.UserAuthView;
import org.junit.Before;
import org.junit.Test;

public class LocationTest {

	private static final BigDecimal LONGITUDE = new BigDecimal("-122.315");
	private static final BigDecimal LATITUDE = new BigDecimal("47.614");
	private static final LocationId LOCATION_ID = new LocationId("LOCA10001");
	private static final AddLocationCommentCommand ADD_LOCATION_COMMENT_COMMAND = new AddLocationCommentCommand(LOCATION_ID, "This place closes early on Mondays");
	private static final String LOCATION_DESCRIPTION = "some interesting description";
	private static final String LOCATION_DESCRIPTION_2 = "a less interesting description of some (or another) location";
	private static final UpdateLocationDescriptionCommand UPDATE_LOCATION_DESCRIPTION = new UpdateLocationDescriptionCommand(LOCATION_ID, LOCATION_DESCRIPTION_2);
	private static final Image IMAGE_1 = new Image("image 1");
	private static final Image IMAGE_2 = new Image("image 2");
	private static final AvailableImages AVAILABLE_IMAGES = new AvailableImages(IMAGE_1, IMAGE_2, null);
	private static final String BEER_NAME = "A beer name";
	private static final BeerAvailableCommand BEER_AVAILABLE_COMMAND = new BeerAvailableCommand(LOCATION_ID, BEER_NAME);
	private static final BeerUnavailableCommand BEER_UNAVAILABLE_COMMAND = new BeerUnavailableCommand(LOCATION_ID, BEER_NAME);
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
	private static final AddBeerReviewCommand ADD_BEER_REVIEW_COMMAND = new AddBeerReviewCommand(LOCATION_ID, BEER_NAME, SILVER, BEER_REVIEW);

	private static final AddLocationReviewCommand ADD_LOCATION_REVIEW_COMMAND = new AddLocationReviewCommand(LOCATION_ID, GOLD, LOCATION_REVIEW);

	private static final ModifyBeerCommand MODIFY_BEER_COMMAND = new ModifyBeerCommand(LOCATION_ID, BEER_NAME, STYLE_2, CATEGORY_2, ABV_2, IBU_2);
	private static final AddLocationCommand ADD_LOCATION_COMMAND = new AddLocationCommand("a location name");
	private static final AddBeerCommand ADD_BEER = new AddBeerCommand(LOCATION_ID, BEER_NAME, STYLE, CATEGORY, ABV, IBU);


	private FixtureConfiguration<Location> fixture;

	@Before
	public void setup() {
		this.fixture = Fixtures.newGivenWhenThenFixture(Location.class);
		this.fixture.registerAnnotatedCommandHandler(new LocationCommandHandler(this.fixture.getRepository(), new LocationCommandHandlerService() {
			LocationIdentifierFactoryStub identifierStub = new LocationIdentifierFactoryStub();
			@Override
			public LocalDateTime now() {
				return DATE_TIME;
			}
			@Override
			public LocationId nextLocationId() {
				return this.identifierStub.next();
			}
		}));
		UserHolder.set(new GuestUserAuth(USER_ID));
	}

	@Test
	public void testAddLocation() {
		this.fixture.givenNoPriorActivity()
			.when(ADD_LOCATION_COMMAND)
			.expectEvents(LocationAddedEvent.fromCommand(LOCATION_ID, ADD_LOCATION_COMMAND));
	}

	@Test
	public void testUpdateLocationDescription() {
		this.fixture.givenCommands(ADD_LOCATION_COMMAND, ADD_BEER)
			.when(UPDATE_LOCATION_DESCRIPTION)
			.expectEvents(new LocationDescriptionUpdatedEvent(LOCATION_ID, LOCATION_DESCRIPTION_2));
	}
	@Test
	public void testUpdateLocationAddress() {
		this.fixture.givenCommands(ADD_LOCATION_COMMAND, ADD_BEER)
			.when(new UpdateLocationAddressCommand(LOCATION_ID, "1013 3rd Street", "3rd floor", "Seattle", "WA", "98102"))
			.expectEvents(new LocationAddressUpdatedEvent(LOCATION_ID, "1013 3rd Street", "3rd floor", "Seattle", "WA", "98102"));
	}
	@Test
	public void testUpdateLocationHoursOfOperation() {
		this.fixture.givenCommands(ADD_LOCATION_COMMAND, ADD_BEER)
			.when(new UpdateLocationHoursOfOperationCommand(LOCATION_ID, "some text hours of operation"))
			.expectEvents(new LocationHoursOfOperationUpdatedEvent(LOCATION_ID, "some text hours of operation"));
	}
	@Test
	public void testUpdateLocationImages() {
		this.fixture.givenCommands(ADD_LOCATION_COMMAND, ADD_BEER)
		.when(new UpdateLocationImagesCommand(LOCATION_ID, AVAILABLE_IMAGES))
		.expectEvents(new LocationImagesUpdatedEvent(LOCATION_ID, AVAILABLE_IMAGES));
	}
	@Test
	public void testUpdateLocationPosition() {
		this.fixture.givenCommands(ADD_LOCATION_COMMAND, ADD_BEER)
			.when(new UpdateLocationPositionCommand(LOCATION_ID, LATITUDE, LONGITUDE))
			.expectEvents(new LocationPositionUpdatedEvent(LOCATION_ID, LATITUDE, LONGITUDE));
	}
	@Test
	public void testUpdateLocationPhone() {
		this.fixture.givenCommands(ADD_LOCATION_COMMAND, ADD_BEER)
			.when(new UpdateLocationPhoneCommand(LOCATION_ID, "206-867-5309"))
			.expectEvents(new LocationPhoneUpdatedEvent(LOCATION_ID, "206-867-5309"));
	}
	@Test
	public void testUpdateLocationWebsite() {
		this.fixture.givenCommands(ADD_LOCATION_COMMAND, ADD_BEER)
			.when(new UpdateLocationWebsiteCommand(LOCATION_ID, "http://some.brewery.com"))
			.expectEvents(new LocationWebsiteUpdatedEvent(LOCATION_ID, "http://some.brewery.com"));
	}

	@Test
	public void testAddBeer() {
		this.fixture.givenCommands(ADD_LOCATION_COMMAND)
			.when(ADD_BEER)
			.expectEvents(BeerAddedEvent.fromCommand(ADD_BEER));
	}

	@Test
	public void testModifyBeer() {
		this.fixture.givenCommands(ADD_LOCATION_COMMAND, ADD_BEER)
			.when(MODIFY_BEER_COMMAND)
			.expectEvents(BeerModifiedEvent.fromCommand(MODIFY_BEER_COMMAND));
	}

	@Test
	public void testBeerUnavailable() {
		this.fixture.givenCommands(ADD_LOCATION_COMMAND, ADD_BEER)
			.when(BEER_UNAVAILABLE_COMMAND)
			.expectEvents(BeerUnavailableEvent.fromCommand(BEER_UNAVAILABLE_COMMAND));
	}
	@Test
	public void testBeerUnavailable_idempotent() {
		this.fixture.givenCommands(ADD_LOCATION_COMMAND, ADD_BEER, BEER_UNAVAILABLE_COMMAND)
			.when(BEER_UNAVAILABLE_COMMAND)
			.expectEvents();
	}

	@Test
	public void testBeerAvailable() {
		this.fixture.givenCommands(ADD_LOCATION_COMMAND, ADD_BEER, BEER_UNAVAILABLE_COMMAND)
			.when(BEER_AVAILABLE_COMMAND)
			.expectEvents(BeerAvailableEvent.fromCommand(BEER_AVAILABLE_COMMAND));
	}
	@Test
	public void testBeerAvailable_idempotent() {
		this.fixture.givenCommands(ADD_LOCATION_COMMAND, ADD_BEER)
			.when(BEER_AVAILABLE_COMMAND)
			.expectEvents();
	}

	@Test
	public void testAddLocationComment() {
		UserHolder.set(userAuth(USER_ID));
		this.fixture.givenCommands(ADD_LOCATION_COMMAND)
			.when(ADD_LOCATION_COMMENT_COMMAND)
			.expectEvents(LocationCommentAddedEvent.fromCommand(ADD_LOCATION_COMMENT_COMMAND, USER_ID, SCREEN_NAME, DATE_TIME));
	}

	@Test
	public void testAddLocationReview_anonymous() {
		this.fixture.givenCommands(ADD_LOCATION_COMMAND, ADD_BEER)
		.when(ADD_LOCATION_REVIEW_COMMAND)
		.expectEvents(
				LocationReviewAddedByAnonymousEvent.fromCommand(ADD_LOCATION_REVIEW_COMMAND, USER_ID, DATE_TIME),
				new LocationRatingUpdatedEvent(LOCATION_ID, GOLD));
	}
	@Test
	public void testAddLocationReview_byUser() {
		UserHolder.set(userAuth(USER_ID));
		this.fixture.givenCommands(ADD_LOCATION_COMMAND, ADD_BEER)
		.when(ADD_LOCATION_REVIEW_COMMAND)
		.expectEvents(
				LocationReviewAddedByUserEvent.fromCommand(ADD_LOCATION_REVIEW_COMMAND, USER_ID, SCREEN_NAME, DATE_TIME),
				new LocationRatingUpdatedEvent(LOCATION_ID, GOLD));
	}
	@Test
	public void testAddBeerReview_anonymous() {
		this.fixture.givenCommands(ADD_LOCATION_COMMAND, ADD_BEER)
			.when(ADD_BEER_REVIEW_COMMAND)
			.expectEvents(
				BeerReviewAddedByAnonymousEvent.fromCommand(ADD_BEER_REVIEW_COMMAND, USER_ID, DATE_TIME),
				new BeerRatingUpdatedEvent(LOCATION_ID, BEER_NAME, SILVER)
				);
	}
	@Test
	public void testAddBeerReview_byUser() {
		UserHolder.set(userAuth(USER_ID));
		this.fixture.givenCommands(ADD_LOCATION_COMMAND, ADD_BEER)
		.when(ADD_BEER_REVIEW_COMMAND)
		.expectEvents(
				BeerReviewAddedByUserEvent.fromCommand(ADD_BEER_REVIEW_COMMAND, USER_ID, SCREEN_NAME, DATE_TIME),
				new BeerRatingUpdatedEvent(LOCATION_ID, BEER_NAME, SILVER)
				);
	}


	private static UserAuthView userAuth(UserId userId) {
		final UserAuthView auth = new UserAuthView();
		auth.userId = userId;
		auth.screenName = SCREEN_NAME;
		return auth;
	}
}
