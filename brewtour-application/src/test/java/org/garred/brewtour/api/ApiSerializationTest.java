package org.garred.brewtour.api;

import org.garred.brewtour.AbstractSerializationTest;
import org.junit.Test;

public class ApiSerializationTest extends AbstractSerializationTest {

	public ApiSerializationTest() {
		super("api");
	}

	@Test
	public void testAddBeer() {
		reflectionValidate(new AddBeer(LOCATION_ID, BEER_NAME, BEER.getStyle(), BEER.getCategory(), BEER.getAbv(), BEER.getIbu()));
	}

	@Test
	public void testAddBeerReview() {
		reflectionValidate(new AddBeerReview(LOCATION_ID, BEER_NAME, BEER_REVIEW));
	}

	@Test
	public void testAddFavoriteLocation() {
		reflectionValidate(new AddFavoriteLocation(LOCATION_ID));
	}

	@Test
	public void testAddLocationReview() {
		reflectionValidate(new AddLocationReview(LOCATION_ID, LOCATION_REVIEW));
	}

	@Test
	public void testBeerAvailable() {
		reflectionValidate(new BeerAvailable(LOCATION_ID, BEER_NAME));
	}

	@Test
	public void testBeerUnavailable() {
		reflectionValidate(new BeerUnavailable(LOCATION_ID, BEER_NAME));
	}

	@Test
	public void testModifyBeer() {
		reflectionValidate(new ModifyBeer(LOCATION_ID, BEER_NAME, BEER.getStyle(), BEER.getCategory(), BEER.getAbv(), BEER.getIbu()));
	}

	@Test
	public void testModifyLocationDescription() {
		reflectionValidate(new ModifyLocationDescription(LOCATION_ID, "a location description"));
	}

	@Test
	public void testRemoveFavorite() {
		reflectionValidate(new RemoveFavoriteLocation(LOCATION_ID));
	}

}
