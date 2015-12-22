package org.garred.brewtour.application;

import static java.util.Arrays.asList;
import static org.garred.brewtour.application.LocaleId.SEATTLE;
import static org.garred.brewtour.application.UserAuth.ADMIN_ROLE;
import static org.garred.brewtour.application.UserAuth.TEST_ROLE;

import java.math.BigDecimal;
import java.util.HashSet;

import org.garred.brewtour.AbstractSerializationTest;
import org.garred.brewtour.application.GoogleMapsParameters.GoogleMapsPosition;
import org.junit.Test;

public class ApplicationSerializationTest extends AbstractSerializationTest {

	public ApplicationSerializationTest() {
		super("application");
	}

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
