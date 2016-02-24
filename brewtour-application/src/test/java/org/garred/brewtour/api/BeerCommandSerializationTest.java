package org.garred.brewtour.api;

import static org.garred.brewtour.AbstractSerializationTest.TestType.COMMAND;
import static org.garred.brewtour.domain.ReviewMedal.GOLD;

import org.garred.brewtour.AbstractSerializationTest;
import org.garred.brewtour.application.command.beer.AddBeerCommand;
import org.garred.brewtour.application.command.beer.AddBeerRatingCommand;
import org.garred.brewtour.application.command.beer.AddBeerReviewCommand;
import org.garred.brewtour.application.command.beer.ModifyBeerCommand;
import org.garred.brewtour.application.command.beer.UpdateBeerImagesCommand;
import org.garred.brewtour.domain.AvailableImages;
import org.junit.Test;

public class BeerCommandSerializationTest extends AbstractSerializationTest {

	public BeerCommandSerializationTest() {
		super("command/beer", "org.garred.brewtour.application.command.beer", COMMAND);
	}

	@Test
	public void testAddBeer() {
		reflectionValidate(new AddBeerCommand(BEER_NAME, BEER_DESCRIPTION, LOCATION_ID_2, BREWERY_NAME, BEER_STYLE, BEER_CATEGORY, BEER_ABV, BEER_IBU));
	}

	@Test
	public void testAddBeerReview() {
		reflectionValidate(new AddBeerReviewCommand(BEER_ID, GOLD, BEER_REVIEW));
	}

	@Test
	public void testModifyBeer() {
		reflectionValidate(new ModifyBeerCommand(BEER_ID, BEER_DESCRIPTION, BEER_STYLE, BEER_CATEGORY, BEER_ABV, BEER_IBU));
	}
	@Test
	public void testUpdateBeerImages() {
		reflectionValidate(new UpdateBeerImagesCommand(BEER_ID, new AvailableImages(IMAGE_1, IMAGE_2, null)));
	}

	@Test
	public void testAddBeerRating() {
		reflectionValidate(new AddBeerRatingCommand(BEER_ID, GOLD));
	}

}
