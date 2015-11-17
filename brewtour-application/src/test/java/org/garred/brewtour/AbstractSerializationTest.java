package org.garred.brewtour;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.garred.brewtour.application.LocationId;
import org.garred.brewtour.infrastructure.ObjectMapperFactory;
import org.junit.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractSerializationTest {

	protected static final LocationId LOCATION_ID = new LocationId("a location id");
	protected static final LocationId LOCATION_ID_2 = new LocationId("another location id");

	private static final ObjectMapper MAPPER = ObjectMapperFactory.objectMapper();

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

	protected static Object retriveStored(Class<?> clazz) throws IOException {
		final InputStream in = new FileInputStream("src/test/resources/json/" + clazz.getSimpleName() + ".json");
		return MAPPER.readValue(in, clazz);
	}
}
