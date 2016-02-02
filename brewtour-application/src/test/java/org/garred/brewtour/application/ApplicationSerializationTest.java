package org.garred.brewtour.application;

import static org.garred.brewtour.domain.LocaleId.SEATTLE;

import org.garred.brewtour.AbstractSerializationTest;
import org.garred.brewtour.domain.AvailableImages;
import org.garred.brewtour.domain.Image;
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
	public void testAvailableImages() {
		validate(new AvailableImages(IMAGE_1, IMAGE_2, null));
	}

	@Test
	public void testLocationId() {
		validate(LOCATION_ID);
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
	public void testUserId() {
		validate(USER_ID);
	}

}
