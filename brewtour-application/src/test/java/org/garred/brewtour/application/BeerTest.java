package org.garred.brewtour.application;

import static org.garred.brewtour.domain.ProfessionalRatingGroup.RATE_BEER;
import static org.garred.brewtour.domain.ReviewMedal.SILVER;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.garred.brewtour.domain.AvailableImages;
import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.Image;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.domain.aggregates.Beer;
import org.garred.brewtour.domain.aggregates.BeerCommandHandler;
import org.garred.brewtour.domain.command.beer.AddBeerCommand;
import org.garred.brewtour.domain.command.beer.AddBeerReviewCommand;
import org.garred.brewtour.domain.command.beer.UpdateBeerAbvCommand;
import org.garred.brewtour.domain.command.beer.UpdateBeerDescriptionCommand;
import org.garred.brewtour.domain.command.beer.UpdateBeerIbuCommand;
import org.garred.brewtour.domain.command.beer.UpdateBeerImagesCommand;
import org.garred.brewtour.domain.command.beer.UpdateBeerProfessionalRatingCommand;
import org.garred.brewtour.domain.command.beer.UpdateBeerSrmCommand;
import org.garred.brewtour.domain.command.beer.UpdateBeerStyleCommand;
import org.garred.brewtour.domain.event.beer.BeerAbvUpdatedEvent;
import org.garred.brewtour.domain.event.beer.BeerAddedEvent;
import org.garred.brewtour.domain.event.beer.BeerDescriptionUpdatedEvent;
import org.garred.brewtour.domain.event.beer.BeerIbuUpdatedEvent;
import org.garred.brewtour.domain.event.beer.BeerProfessionalRatingUpdatedEvent;
import org.garred.brewtour.domain.event.beer.BeerRatingUpdatedEvent;
import org.garred.brewtour.domain.event.beer.BeerReviewAddedByAnonymousEvent;
import org.garred.brewtour.domain.event.beer.BeerReviewAddedByUserEvent;
import org.garred.brewtour.domain.event.beer.BeerSrmUpdatedEvent;
import org.garred.brewtour.domain.event.beer.BeerStyleUpdatedEvent;
import org.garred.brewtour.security.GuestUserAuth;
import org.garred.brewtour.security.UserHolder;
import org.garred.brewtour.service.BeerCommandHandlerService;
import org.garred.brewtour.view.UserAuthView;
import org.junit.Before;
import org.junit.Test;

public class BeerTest {

	private static final String BEER_RATING_LINK = "http://www.ratebeer.com/beer/fremont-universale-pale-ale/104575/";
	private static final BeerId BEER_ID = new BeerId("BEER10101");
	protected static final String BEER_DESCRIPTION = "a very tasty beer";
	protected static final String BREWERY_NAME = "Stone Brewing";

	private static final LocationId LOCATION_ID = new LocationId("LOCA10001");
	private static final Image IMAGE_1 = new Image("image 1");
	private static final Image IMAGE_2 = new Image("image 2");
	private static final AvailableImages AVAILABLE_IMAGES = new AvailableImages(IMAGE_1, IMAGE_2, null);
	private static final UpdateBeerImagesCommand UPDATE_BEER_IMAGES_COMMAND = new UpdateBeerImagesCommand(BEER_ID, AVAILABLE_IMAGES);
	private static final String BEER_NAME = "A beer name";
	private static final String STYLE = "Beer Style";
	private static final BigDecimal ABV = new BigDecimal("6.3");
	private static final BigDecimal IBU = new BigDecimal("76");
	private static final BigDecimal SRM = new BigDecimal("20");
	protected static final UserId USER_ID = new UserId("a user id");
	private static final String SCREEN_NAME = "User ScreenName";
	protected static final LocalDateTime DATE_TIME = LocalDateTime.of(2015, 9, 20, 8, 50);
	private static final String BEER_REVIEW = "spicy but with a full body";
	private static final AddBeerReviewCommand ADD_BEER_REVIEW_COMMAND = new AddBeerReviewCommand(BEER_ID, SILVER, BEER_REVIEW);

	private static final AddBeerCommand ADD_BEER = new AddBeerCommand(BEER_NAME, LOCATION_ID, BREWERY_NAME);
	private static final UpdateBeerDescriptionCommand UPDATE_BEER_DESCRIPTION = new UpdateBeerDescriptionCommand(BEER_ID, BEER_DESCRIPTION);
	private static final UpdateBeerStyleCommand UPDATE_BEER_STYLE = new UpdateBeerStyleCommand(BEER_ID, STYLE);
	private static final UpdateBeerAbvCommand UPDATE_BEER_ABV = new UpdateBeerAbvCommand(BEER_ID, ABV);
	private static final UpdateBeerIbuCommand UPDATE_BEER_IBU = new UpdateBeerIbuCommand(BEER_ID, IBU);
	private static final UpdateBeerSrmCommand UPDATE_BEER_SRM = new UpdateBeerSrmCommand(BEER_ID, SRM);


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
	public void testUpdateBeerDescription() {
		this.fixture.givenCommands(ADD_BEER)
		.when(UPDATE_BEER_DESCRIPTION)
		.expectEvents(new BeerDescriptionUpdatedEvent(BEER_ID, BEER_DESCRIPTION));
	}
	@Test
	public void testUpdateBeerStyle() {
		this.fixture.givenCommands(ADD_BEER)
		.when(UPDATE_BEER_STYLE)
		.expectEvents(new BeerStyleUpdatedEvent(BEER_ID, STYLE));
	}
	@Test
	public void testUpdateBeerAbv() {
		this.fixture.givenCommands(ADD_BEER)
		.when(UPDATE_BEER_ABV)
		.expectEvents(new BeerAbvUpdatedEvent(BEER_ID, ABV));
	}
	@Test
	public void testUpdateBeerIbu() {
		this.fixture.givenCommands(ADD_BEER)
		.when(UPDATE_BEER_IBU)
		.expectEvents(new BeerIbuUpdatedEvent(BEER_ID, IBU));
	}
	@Test
	public void testUpdateBeerSrm() {
		this.fixture.givenCommands(ADD_BEER)
		.when(UPDATE_BEER_SRM)
		.expectEvents(new BeerSrmUpdatedEvent(BEER_ID, SRM));
	}
	@Test
	public void testUpdateBeerImages_idempotent() {
		this.fixture.givenCommands(ADD_BEER,UPDATE_BEER_IMAGES_COMMAND)
		.when(UPDATE_BEER_IMAGES_COMMAND)
		.expectEvents();
	}

	@Test
	public void testUpdateBeerProfessionalRating() {
		this.fixture.givenCommands(ADD_BEER)
		.when(new UpdateBeerProfessionalRatingCommand(BEER_ID, RATE_BEER, BEER_RATING_LINK, new BigDecimal("89"), new BigDecimal("100")))
		.expectEvents(
				new BeerProfessionalRatingUpdatedEvent(BEER_ID, RATE_BEER, BEER_RATING_LINK, new BigDecimal("89"), new BigDecimal("100"), DATE_TIME),
				new BeerRatingUpdatedEvent(BEER_ID, SILVER)
				);
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
