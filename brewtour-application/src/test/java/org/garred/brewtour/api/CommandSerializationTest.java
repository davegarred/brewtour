package org.garred.brewtour.api;

import org.garred.brewtour.AbstractSerializationTest;
import org.garred.brewtour.application.command.location.AddBeerCommand;
import org.garred.brewtour.application.command.location.AddBeerReviewCommand;
import org.garred.brewtour.application.command.location.AddLocationReviewCommand;
import org.garred.brewtour.application.command.location.BeerAvailableCommand;
import org.garred.brewtour.application.command.location.BeerUnavailableCommand;
import org.garred.brewtour.application.command.location.ModifyBeerCommand;
import org.garred.brewtour.application.command.location.UpdateLocationDescriptionCommand;
import org.junit.Test;

public class CommandSerializationTest extends AbstractSerializationTest {

	public CommandSerializationTest() {
		super("api");
	}

	@Test
	public void testAddBeer() {
		reflectionValidate(new AddBeerCommand(LOCATION_ID, BEER_NAME, BEER_STYLE, BEER_CATEGORY, BEER_ABV, BEER_IBU));
	}

	@Test
	public void testAddBeerReview() {
		reflectionValidate(new AddBeerReviewCommand(LOCATION_ID, BEER_NAME, 4, BEER_REVIEW));
	}

	@Test
	public void testAddLocationReview() {
		reflectionValidate(new AddLocationReviewCommand(LOCATION_ID, 4, LOCATION_REVIEW));
	}

	@Test
	public void testBeerAvailable() {
		reflectionValidate(new BeerAvailableCommand(LOCATION_ID, BEER_NAME));
	}

	@Test
	public void testBeerUnavailable() {
		reflectionValidate(new BeerUnavailableCommand(LOCATION_ID, BEER_NAME));
	}

	@Test
	public void testModifyBeer() {
		reflectionValidate(new ModifyBeerCommand(LOCATION_ID, BEER_NAME, BEER_STYLE, BEER_CATEGORY, BEER_ABV, BEER_IBU));
	}

	@Test
	public void testModifyLocationDescription() {
		reflectionValidate(new UpdateLocationDescriptionCommand(LOCATION_ID, "a location description"));
	}

}
