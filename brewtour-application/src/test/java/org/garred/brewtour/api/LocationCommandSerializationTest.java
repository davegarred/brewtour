package org.garred.brewtour.api;

import static org.garred.brewtour.AbstractSerializationTest.TestType.COMMAND;
import static org.garred.brewtour.domain.ReviewMedal.SILVER;

import java.math.BigDecimal;

import org.garred.brewtour.AbstractSerializationTest;
import org.garred.brewtour.application.command.location.AddLocationCommand;
import org.garred.brewtour.application.command.location.AddLocationCommentCommand;
import org.garred.brewtour.application.command.location.AddLocationRatingCommand;
import org.garred.brewtour.application.command.location.AddLocationReviewCommand;
import org.garred.brewtour.application.command.location.BeerAvailableCommand;
import org.garred.brewtour.application.command.location.BeerUnavailableCommand;
import org.garred.brewtour.application.command.location.UpdateLocationAddressCommand;
import org.garred.brewtour.application.command.location.UpdateLocationDescriptionCommand;
import org.garred.brewtour.application.command.location.UpdateLocationHoursOfOperationCommand;
import org.garred.brewtour.application.command.location.UpdateLocationImagesCommand;
import org.garred.brewtour.application.command.location.UpdateLocationPhoneCommand;
import org.garred.brewtour.application.command.location.UpdateLocationPositionCommand;
import org.garred.brewtour.application.command.location.UpdateLocationWebsiteCommand;
import org.garred.brewtour.domain.AvailableImages;
import org.junit.Test;

public class LocationCommandSerializationTest extends AbstractSerializationTest {

	public LocationCommandSerializationTest() {
		super("command/location", "org.garred.brewtour.application.command.location", COMMAND);
	}

	@Test
	public void testAddLocation() {
		reflectionValidate(new AddLocationCommand("Stone Brewing"));
	}

	@Test
	public void testAddLocationReview() {
		reflectionValidate(new AddLocationReviewCommand(LOCATION_ID, SILVER, LOCATION_REVIEW));
	}

	@Test
	public void testBeerAvailable() {
		reflectionValidate(new BeerAvailableCommand(LOCATION_ID, BEER_ID));
	}

	@Test
	public void testBeerUnavailable() {
		reflectionValidate(new BeerUnavailableCommand(LOCATION_ID, BEER_ID));
	}

	@Test
	public void testAddLocationComment() {
		reflectionValidate(new AddLocationCommentCommand(LOCATION_ID, "a comment location"));
	}

	@Test
	public void testUpdateLocationImages() {
		reflectionValidate(new UpdateLocationImagesCommand(LOCATION_ID, new AvailableImages(IMAGE_1, IMAGE_2, null)));
	}

	@Test
	public void testUpdateLocationAddress() {
		reflectionValidate(new UpdateLocationAddressCommand(LOCATION_ID, "street address", "apt", "Seattle", "WA", "98109"));
	}

	@Test
	public void testUpdateLocationDescription() {
		reflectionValidate(new UpdateLocationDescriptionCommand(LOCATION_ID, "A cute little brewpub to hang out"));
	}

	@Test
	public void testUpdateLocationPosition() {
		reflectionValidate(new UpdateLocationPositionCommand(LOCATION_ID, new BigDecimal("47.614"), new BigDecimal("-122.315")));
	}

	@Test
	public void testUpdateLocationHoursOfOperation() {
		reflectionValidate(new UpdateLocationHoursOfOperationCommand(LOCATION_ID, "Mon - Fri, 8-5"));
	}

	@Test
	public void testUpdateLocationPhone() {
		reflectionValidate(new UpdateLocationPhoneCommand(LOCATION_ID, "206-867-5309"));
	}

	@Test
	public void testUpdateLocationWebsite() {
		reflectionValidate(new UpdateLocationWebsiteCommand(LOCATION_ID, "http://nullgeodesic.com"));
	}

	@Test
	public void testAddLocationRating() {
		reflectionValidate(new AddLocationRatingCommand(LOCATION_ID, SILVER));
	}

}
