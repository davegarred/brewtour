package org.garred.brewtour.application;

import static java.util.Arrays.asList;
import static org.garred.brewtour.application.LocaleId.SEATTLE;
import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.garred.brewtour.infrastructure.ObjectMapperFactory;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SerializationTest {

	private static final Beer BEER = new Beer("beer id", "a beer name", "someStatus", "awesome style", "niche category", new BigDecimal("6.2"));
	private static final LocationId LOCATION_ID = new LocationId("locationId");
	private static final Image IMAGE_2 = new Image("image 2");
	private static final Image IMAGE_1 = new Image("image 1");
	private static final LocalePoint LOCALE_POINT = new LocalePoint(LOCATION_ID, "Brewery Name", "A nice little description of the brewery",
			new BigDecimal("47.614"), new BigDecimal("-122.315"), new AvailableImages(IMAGE_1, IMAGE_2, null));
	private static final ObjectMapper MAPPER = ObjectMapperFactory.objectMapper();


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
	public void testLocation() {
		validate(new Location(LOCATION_ID, "someBrewDbId", "Brewery Name", "A nice little description of the brewery",
				new BigDecimal("47.614"), new BigDecimal("-122.315"), new AvailableImages(IMAGE_1, IMAGE_2, null), asList(BEER)));
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
		validate(new Locale(SEATTLE, "Brewery Name", new BigDecimal("47.614"), new BigDecimal("-122.315"), 8, asList(LOCALE_POINT)));
	}


	protected <T> void validate(T object) {
		try {
			System.out.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object));
			final String serializedObject = MAPPER.writeValueAsString(object);
			final Object deserializedObject = MAPPER.readValue(serializedObject, object.getClass());
			assertEquals("Serialization/deserialization process has modified object", object, deserializedObject);

			final Object storedObject = retriveStored(object.getClass());
			assertEquals("The test object has changed from the stored reference instance", storedObject, object);
		} catch (final IOException e) {
			Assert.fail(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	private static Object retriveStored(Class<?> clazz) throws IOException {
		final InputStream in = new FileInputStream("src/test/resources/json/" + clazz.getSimpleName() + ".json");
		return MAPPER.readValue(in, clazz);
	}
}
