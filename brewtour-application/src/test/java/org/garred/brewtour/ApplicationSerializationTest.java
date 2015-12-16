package org.garred.brewtour;

import static java.util.Arrays.asList;
import static org.garred.brewtour.application.LocaleId.SEATTLE;
import static org.garred.brewtour.application.UserAuth.ADMIN_ROLE;
import static org.garred.brewtour.application.UserAuth.TEST_ROLE;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;

import org.garred.brewtour.application.AvailableImages;
import org.garred.brewtour.application.Beer;
import org.garred.brewtour.application.GoogleMapsParameters;
import org.garred.brewtour.application.GoogleMapsParameters.GoogleMapsPosition;
import org.garred.brewtour.application.Image;
import org.garred.brewtour.application.Locale;
import org.garred.brewtour.application.LocalePoint;
import org.garred.brewtour.application.Location;
import org.garred.brewtour.application.Review;
import org.garred.brewtour.application.UserAuth;
import org.garred.brewtour.application.UserDetails;
import org.garred.brewtour.application.UserId;
import org.junit.Test;

public class ApplicationSerializationTest extends AbstractSerializationTest {

	private static final LocalDateTime DATE_TIME = LocalDateTime.of(2015, 9, 20, 8, 50);
	private static final String LOGIN = "a user login";
	private static final UserId USER_ID = new UserId("a user id");
	private static final Review LOCATION_REVIEW = new Review(USER_ID, DATE_TIME, 5, "A nice place to hang out");
	private static final Review BEER_REVIEW = new Review(USER_ID, DATE_TIME, 4, "spicy but with a full body");

	private static final Beer BEER = new Beer("beer id", "a beer name", "someStatus", "awesome style", "niche category",
			new BigDecimal("6.2"), new BigDecimal("45"), true, asList(BEER_REVIEW));
	private static final Image IMAGE_2 = new Image("image 2");
	private static final Image IMAGE_1 = new Image("image 1");
	private static final LocalePoint LOCALE_POINT = new LocalePoint(LOCATION_ID, "Brewery Name",
			new BigDecimal("47.614"), new BigDecimal("-122.315"), new AvailableImages(IMAGE_1, IMAGE_2, null));

	@Test
	public void testImage() {
		validate(new Image("test value"));
	}

	@Test
	public void testBeer() {
		validate(BEER);
	}

	@Test
	public void testAvailableImages() {
		validate(new AvailableImages(IMAGE_1, IMAGE_2, null));
	}

	@Test
	public void testLocationId() {
		validate(LOCATION_ID);
	}

	@Test
	public void testReview() {
		validate(LOCATION_REVIEW);
	}

	@Test
	public void testLocation() {
		validate(new Location(LOCATION_ID, "someBrewDbId", "Brewery Name", "A nice little description of the brewery",
				new BigDecimal("47.614"), new BigDecimal("-122.315"), new AvailableImages(IMAGE_1, IMAGE_2, null), asList(BEER), asList(LOCATION_REVIEW)));
	}

	@Test
	public void testLocaleId() {
		validate(SEATTLE);
	}

	@Test
	public void testLocalePoint() {
		validate(LOCALE_POINT);
	}

	@Test
	public void testLocale() {
		final GoogleMapsParameters mapParams = new GoogleMapsParameters(new GoogleMapsPosition(new BigDecimal("47.614"), new BigDecimal("-122.315")), 8);
		validate(new Locale(SEATTLE, "Brewery Name", mapParams, asList(LOCALE_POINT)));
	}

	@Test
	public void testUserId() {
		validate(USER_ID);
	}

	@Test
	public void testUserDetails() {
		validate(new UserDetails(USER_ID, new HashSet<>(asList(LOCATION_ID, LOCATION_ID_2))));
	}

	@Test
	public void testUserAuth() {
		validate(new UserAuth(USER_ID, LOGIN,  new HashSet<>(asList(TEST_ROLE, ADMIN_ROLE))));
	}

}
