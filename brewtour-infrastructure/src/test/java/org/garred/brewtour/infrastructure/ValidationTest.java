package org.garred.brewtour.infrastructure;

import static java.lang.String.format;
import static java.lang.String.join;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Validator;

import org.garred.brewtour.application.command.beer.AddBeerCommand;
import org.garred.brewtour.application.command.beer.UpdateBeerAbvCommand;
import org.garred.brewtour.application.command.beer.UpdateBeerDescriptionCommand;
import org.garred.brewtour.application.command.beer.UpdateBeerIbuCommand;
import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.LocationId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/validator-config.xml")
public class ValidationTest {

	private static final BeerId BEER_ID = new BeerId("B1001");
	@Autowired
	private Validator validator;

	@Test
	public void testValid() {
		valid(new AddBeerCommand("A beer", new LocationId("locId"), "A brewery"));
		valid(new UpdateBeerDescriptionCommand(BEER_ID, "description"));
	}

	@Test
	public void testNull() {
		final AddBeerCommand command = new AddBeerCommand(null, null, null);
		assertMessage(command, "beerName may not be null", "breweryId may not be null", "breweryName may not be null");
	}

	@Test
	public void testNullId() {
		final UpdateBeerDescriptionCommand command = new UpdateBeerDescriptionCommand(null, "description");
		assertMessage(command, "beerId may not be null");
	}

	@Test
	public void testAbv() {
		valid(new UpdateBeerAbvCommand(BEER_ID, new BigDecimal("6.2")));

		final UpdateBeerAbvCommand command = new UpdateBeerAbvCommand(BEER_ID, new BigDecimal("-0.10"));
		assertMessage(command, "abv must be greater than or equal to 0.0", "abv numeric value out of bounds (<2 digits>.<1 digits> expected)");
	}

	@Test
	public void testIbu() {
		valid(new UpdateBeerIbuCommand(BEER_ID, new BigDecimal("40")));

		final UpdateBeerIbuCommand command = new UpdateBeerIbuCommand(BEER_ID, new BigDecimal("-0.1"));
		assertMessage(command, "ibu must be greater than or equal to 0", "ibu numeric value out of bounds (<3 digits>.<0 digits> expected)");
	}

	private void valid(Object command) {
		assertTrue(this.validator.validate(command).isEmpty());
	}
	private void assertMessage(Object command, String... messages) {
		final List<String> expectedMessages = Arrays.asList(messages);
		final Set<String> resultMessages = this.validator.validate(command).stream().map(v -> v.getPropertyPath().toString() + " " + v.getMessage()).collect(Collectors.toSet());
		assertEquals(expectedMessages.size(), resultMessages.size());
		for(final String message : expectedMessages) {
			assertTrue(format("Expected message '%s' was not found in validation results\n - %s", message, join("\n - ", resultMessages)),resultMessages.contains(message));
			resultMessages.remove(message);
		}
	}

}
