package org.garred.brewtour.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.garred.brewtour.api.AddBeer;
import org.garred.brewtour.api.BeerAvailable;
import org.garred.brewtour.api.BeerUnavailable;
import org.garred.brewtour.api.ModifyBeer;
import org.garred.brewtour.api.ModifyLocationDescription;
import org.junit.Before;
import org.junit.Test;

public class LocationTest {

	private static final String LOCATION_DESCRIPTION = "some interesting description";
	private static final String LOCATION_DESCRIPTION_2 = "a less interesting description of some (or another) location";
	private static final String BEER_NAME = "A beer name";
	private static final String STYLE = "Beer Style";
	private static final String STYLE_2 = "another beer Style";
	private static final String CATEGORY = "beer category";
	private static final String CATEGORY_2 = "another beer category";
	private static final BigDecimal ABV = new BigDecimal("6.3");
	private static final BigDecimal ABV_2 = new BigDecimal("5.1");
	private static final BigDecimal IBU = new BigDecimal("76");
	private static final BigDecimal IBU_2 = new BigDecimal("29");
	private static final Beer BEER = new Beer(null, BEER_NAME, null, STYLE, CATEGORY, ABV, IBU, true, new ArrayList<>());
	private static final Beer BEER_2 = new Beer(null, BEER_NAME, null, STYLE_2, CATEGORY_2, ABV_2, IBU_2, true, new ArrayList<>());
	private static final LocationId LOCATION_ID = new LocationId("a location id");
	private static final AddBeer ADD_BEER = new AddBeer(LOCATION_ID, BEER_NAME, STYLE, CATEGORY, ABV, IBU);
	private static final Image IMAGE_2 = new Image("image 2");
	private static final Image IMAGE_1 = new Image("image 1");

	private static Location location(LocationId locationId) {
		return new Location(locationId, null, "a location name", LOCATION_DESCRIPTION,
				new BigDecimal("47.614"), new BigDecimal("-122.315"),
				new AvailableImages(IMAGE_1, IMAGE_2, null), new ArrayList<>(), new ArrayList<>());
	}

	private Location location;

	@Before
	public void setup() {
		this.location = location(LOCATION_ID);
	}

	@Test
	public void testAddBeer() {
		this.location.addBeer(ADD_BEER);
		assertSingleItemInCollection(BEER, this.location.beers);
	}

	@Test
	public void testModifyBeer() {
		this.location.addBeer(ADD_BEER);
		final ModifyBeer modify = new ModifyBeer(LOCATION_ID, BEER_NAME, STYLE_2, CATEGORY_2, ABV_2, IBU_2);
		this.location.modifyBeer(modify);
		assertSingleItemInCollection(BEER_2, this.location.beers);
	}

	@Test
	public void testBeerUnavailable() {
		this.location.addBeer(ADD_BEER);
		this.location.beerUnavailable(new BeerUnavailable(LOCATION_ID, BEER_NAME));
		assertFalse(this.location.beers.get(0).isAvailable());
	}

	@Test
	public void testBeerAvailable() {
		this.location.addBeer(ADD_BEER);
		this.location.beerUnavailable(new BeerUnavailable(LOCATION_ID, BEER_NAME));
		this.location.beerAvailable(new BeerAvailable(LOCATION_ID, BEER_NAME));
		assertTrue(this.location.beers.get(0).isAvailable());
	}

	@Test
	public void testModifyLocationDescription() {
		this.location.modifyLocationDescription(new ModifyLocationDescription(LOCATION_ID, LOCATION_DESCRIPTION_2));
		assertEquals(LOCATION_DESCRIPTION_2, this.location.description);
	}


	private static <T> void assertSingleItemInCollection(T object, Collection<T> collection) {
		assertEquals(1, collection.size());
		assertEquals(object, collection.iterator().next());
	}
}
