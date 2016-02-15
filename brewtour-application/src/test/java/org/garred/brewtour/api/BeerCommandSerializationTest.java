package org.garred.brewtour.api;

import static org.garred.brewtour.AbstractSerializationTest.TestType.COMMAND;
import static org.garred.brewtour.domain.ReviewMedal.GOLD;

import org.garred.brewtour.AbstractSerializationTest;
import org.garred.brewtour.application.command.beer.AddBeerCommand;
import org.garred.brewtour.application.command.beer.AddBeerRatingCommand;
import org.garred.brewtour.application.command.beer.AddBeerReviewCommand;
import org.garred.brewtour.application.command.beer.ModifyBeerCommand;
import org.junit.Test;

public class BeerCommandSerializationTest extends AbstractSerializationTest {

	public BeerCommandSerializationTest() {
		super("command/beer", "org.garred.brewtour.application.command.beer", COMMAND);
	}

	@Test
	public void testAddBeer() {
		reflectionValidate(new AddBeerCommand(BEER_NAME, BREWERY_ID, BREWERY_NAME, BEER_STYLE, BEER_CATEGORY, BEER_ABV, BEER_IBU));
	}

	@Test
	public void testAddBeerReview() {
		reflectionValidate(new AddBeerReviewCommand(BEER_ID, GOLD, BEER_REVIEW));
	}

	@Test
	public void testModifyBeer() {
		reflectionValidate(new ModifyBeerCommand(BEER_ID, BEER_STYLE, BEER_CATEGORY, BEER_ABV, BEER_IBU));
	}

	@Test
	public void testAddBeerRating() {
		reflectionValidate(new AddBeerRatingCommand(BEER_ID, GOLD));
	}

}
