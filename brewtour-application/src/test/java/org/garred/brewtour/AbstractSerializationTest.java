package org.garred.brewtour;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.apache.commons.io.IOUtils;
import org.garred.brewtour.domain.AvailableImages;
import org.garred.brewtour.domain.Beer;
import org.garred.brewtour.domain.Image;
import org.garred.brewtour.domain.LocalePoint;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.Review;
import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.infrastructure.ObjectMapperFactory;
import org.junit.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractSerializationTest {

	protected static final String BEER_NAME = "a beer name";
	protected static final LocationId LOCATION_ID = new LocationId("a location id");
	protected static final LocationId LOCATION_ID_2 = new LocationId("another location id");
	protected static final LocalDateTime DATE_TIME = LocalDateTime.of(2015, 9, 20, 8, 50);
	protected static final String LOGIN = "a user login";
	protected static final UserId USER_ID = new UserId("a user id");
	protected static final Review LOCATION_REVIEW = new Review(USER_ID, DATE_TIME, 5, "A nice place to hang out");
	protected static final Review BEER_REVIEW = new Review(USER_ID, DATE_TIME, 4, "spicy but with a full body");

	protected static final Beer BEER = new Beer("beer id", BEER_NAME, "someStatus", "awesome style", "niche category",
			new BigDecimal("6.2"), new BigDecimal("45"), true, asList(BEER_REVIEW));
	protected static final Image IMAGE_2 = new Image("image 2");
	protected static final Image IMAGE_1 = new Image("image 1");
	protected static final LocalePoint LOCALE_POINT = new LocalePoint(LOCATION_ID, "Brewery Name",
			new BigDecimal("47.614"), new BigDecimal("-122.315"), new AvailableImages(IMAGE_1, IMAGE_2, null));


	private static final ObjectMapper MAPPER = ObjectMapperFactory.objectMapper();

	private final String folder;

	public AbstractSerializationTest(String folder) {
		this.folder = folder;
	}

	protected <T> void reflectionValidate(T object) {
		try {
			System.out.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object));
			final String serializedObject = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object);
			final Object deserializedObject = MAPPER.readValue(serializedObject, object.getClass());
			assertTrue("Serialization/deserialization process has modified object", reflectionEquals(object, deserializedObject, true));

			final String storedRaw = retriveRawStored(object.getClass());
			assertEquals("The test object no longer serializes identical to the stored reference instance", storedRaw, serializedObject);

			final Object storedObject = retriveStored(object.getClass());
			assertTrue("The test object has changed from the stored reference instance", reflectionEquals(storedObject, object, true));
		} catch (final IOException e) {
			Assert.fail(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	protected <T> void validate(T object) {
		try {
			System.out.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object));
			final String serializedObject = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object);
			final Object deserializedObject = MAPPER.readValue(serializedObject, object.getClass());
			assertEquals("Serialization/deserialization process has modified object", object, deserializedObject);

			final String storedRaw = retriveRawStored(object.getClass());
			assertEquals("The test object no longer serializes identical to the stored reference instance", storedRaw, serializedObject);

			final Object storedObject = retriveStored(object.getClass());
			assertEquals("The test object has changed from the stored reference instance", storedObject, object);
		} catch (final IOException e) {
			Assert.fail(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	protected String retriveRawStored(Class<?> clazz) throws IOException {
		final InputStream in = new FileInputStream(format("src/test/resources/json/%s/%s.json", this.folder, clazz.getSimpleName()));
		return IOUtils.toString(in);
	}
	protected Object retriveStored(Class<?> clazz) throws IOException {
		final InputStream in = new FileInputStream(format("src/test/resources/json/%s/%s.json", this.folder, clazz.getSimpleName()));
		return MAPPER.readValue(in, clazz);
	}
}
