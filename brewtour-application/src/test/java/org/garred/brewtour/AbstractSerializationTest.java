package org.garred.brewtour;

import static java.lang.String.format;
import static java.util.regex.Pattern.compile;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.garred.brewtour.domain.AvailableImages;
import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.BreweryId;
import org.garred.brewtour.domain.Image;
import org.garred.brewtour.domain.LocalePoint;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.infrastructure.ObjectMapperFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractSerializationTest {

	protected static final String BREWERY_NAME = "Stone Brewing";
	protected static final BreweryId BREWERY_ID = new BreweryId("BREW10001");
	protected static final BeerId BEER_ID = new BeerId("BEER10101");

	protected static final String BEER_CATEGORY = "niche category";
	protected static final String BEER_STYLE = "awesome style";
	protected static final String BEER_STATUS = "someStatus";
	protected static final BigDecimal BEER_IBU = new BigDecimal("45");
	protected static final BigDecimal BEER_ABV = new BigDecimal("6.2");
	protected static final String BEER_REVIEW = "spicy but with a full body";
	protected static final String LOCATION_REVIEW = "A nice place to hang out";
	protected static final String BEER_NAME = "a beer name";
	protected static final LocationId LOCATION_ID = new LocationId("a location id");
	protected static final LocationId LOCATION_ID_2 = new LocationId("another location id");
	protected static final LocalDateTime DATE_TIME = LocalDateTime.of(2015, 9, 20, 8, 50);
	protected static final String LOGIN = "a user login";
	protected static final UserId USER_ID = new UserId("a user id");

	protected static final Image IMAGE_2 = new Image("image 2");
	protected static final Image IMAGE_1 = new Image("image 1");
	protected static final LocalePoint LOCALE_POINT = new LocalePoint(LOCATION_ID, "Brewery Name",
			new BigDecimal("47.614"), new BigDecimal("-122.315"), new AvailableImages(IMAGE_1, IMAGE_2, null));


	private static final ObjectMapper MAPPER = ObjectMapperFactory.objectMapper();

	private final String folder;
	private final String packageName;
	private final TestType testType;

	public AbstractSerializationTest(String jsonFolder, String packageName, TestType testType) {
		this.folder = jsonFolder;
		this.packageName = packageName;
		this.testType = testType;
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

	@Test
	public void testAudit() {
		if(this.packageName == null && this.testType == null) {
			return;
		}
		final ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(this.testType.filter);
		final Set<BeanDefinition> components = scanner.findCandidateComponents(this.packageName);
		final List<String> requiredTests = new ArrayList<>();
		for(final BeanDefinition beanDef : components) {
			final String commandName = beanDef.getBeanClassName();
			requiredTests.add(commandName.substring(commandName.lastIndexOf(".") + 1));
		}
		final List<String> foundMethods = new ArrayList<>();
		for(final Method method : this.getClass().getMethods()) {
			if(method.isAnnotationPresent(Test.class)) {
				final String methodName = method.getName();
				foundMethods.add(methodName.substring(4) + this.testType.classSuffix);
			}
		}
		final List<String> missingTests = new ArrayList<>();
		for(final String requiredTest : requiredTests) {
			if(!foundMethods.contains(requiredTest)) {
				missingTests.add(requiredTest);
			}
		}
		if(!missingTests.isEmpty()) {
			fail("Missing test(s) for \n  - " + String.join("\n  - ", missingTests));
		}
	}

	public enum TestType {
		COMMAND("Command", new RegexPatternTypeFilter(compile(".*Command$"))),
		EVENT("Event", new RegexPatternTypeFilter(compile(".*Event$")));

		public final String classSuffix;
		public final TypeFilter filter;
		private TestType(String classSuffix, TypeFilter filter) {
			this.classSuffix = classSuffix;
			this.filter = filter;
		}
	}
}
