package org.garred.brewtour.api;

import org.garred.brewtour.AbstractSerializationTest;
import org.junit.Test;

public class CommandSerializationTest extends AbstractSerializationTest {

	public CommandSerializationTest() {
		super("api");
	}

	@Test
	public void testAddBeer() {
		reflectionValidate(new AddBeerCommand(LOCATION_ID, BEER_NAME, BEER.getStyle(), BEER.getCategory(), BEER.getAbv(), BEER.getIbu()));
	}

	@Test
	public void testAddBeerReview() {
		reflectionValidate(new AddBeerReviewCommand(LOCATION_ID, BEER_NAME, BEER_REVIEW));
	}

	@Test
	public void testAddFavoriteLocation() {
		reflectionValidate(new AddFavoriteLocationCommand(LOCATION_ID));
	}

	@Test
	public void testAddLocationReview() {
		reflectionValidate(new AddLocationReviewCommand(LOCATION_ID, LOCATION_REVIEW));
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
		reflectionValidate(new ModifyBeerCommand(LOCATION_ID, BEER_NAME, BEER.getStyle(), BEER.getCategory(), BEER.getAbv(), BEER.getIbu()));
	}

	@Test
	public void testModifyLocationDescription() {
		reflectionValidate(new ModifyLocationDescriptionCommand(LOCATION_ID, "a location description"));
	}

	@Test
	public void testRemoveFavorite() {
		reflectionValidate(new RemoveFavoriteLocationCommand(LOCATION_ID));
	}

}
