package org.garred.brewtour;

import org.garred.brewtour.api.AddFavoriteLocation;
import org.garred.brewtour.api.RemoveFavoriteLocation;
import org.junit.Test;

public class ApiSerializationTest extends AbstractSerializationTest {


	@Test
	public void testAddFavorite() {
		validate(new AddFavoriteLocation(LOCATION_ID));
	}

	@Test
	public void testRemoveFavorite() {
		validate(new RemoveFavoriteLocation(LOCATION_ID));
	}

}
