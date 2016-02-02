package org.garred.brewtour.controller.api;

import static java.lang.String.format;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.garred.brewtour.infrastructure.ObjectMapperFactory;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WebappApiSerializationTest {


	private static final ObjectMapper MAPPER = ObjectMapperFactory.objectMapper();
	private static final String FOLDER = "api";

	@Test
	public void testUserLogin() {
		reflectionValidate(new UserLogin("dave", "davespassword"));
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

	protected static String retriveRawStored(Class<?> clazz) throws IOException {
		final InputStream in = new FileInputStream(format("src/test/resources/json/%s/%s.json", FOLDER, clazz.getSimpleName()));
		return IOUtils.toString(in);
	}
	protected static Object retriveStored(Class<?> clazz) throws IOException {
		final InputStream in = new FileInputStream(format("src/test/resources/json/%s/%s.json", FOLDER, clazz.getSimpleName()));
		return MAPPER.readValue(in, clazz);
	}

}
