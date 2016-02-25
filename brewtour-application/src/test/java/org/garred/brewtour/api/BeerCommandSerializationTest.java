package org.garred.brewtour.api;

import static org.garred.brewtour.AbstractSerializationTest.TestType.COMMAND;
import static org.garred.brewtour.domain.ProfessionalRatingGroup.RATE_BEER;
import static org.garred.brewtour.domain.ReviewMedal.GOLD;

import java.math.BigDecimal;

import org.garred.brewtour.AbstractSerializationTest;
import org.garred.brewtour.application.command.beer.AddBeerCommand;
import org.garred.brewtour.application.command.beer.AddBeerRatingCommand;
import org.garred.brewtour.application.command.beer.AddBeerReviewCommand;
import org.garred.brewtour.application.command.beer.UpdateBeerAbvCommand;
import org.garred.brewtour.application.command.beer.UpdateBeerDescriptionCommand;
import org.garred.brewtour.application.command.beer.UpdateBeerIbuCommand;
import org.garred.brewtour.application.command.beer.UpdateBeerImagesCommand;
import org.garred.brewtour.application.command.beer.UpdateBeerProfessionalRatingCommand;
import org.garred.brewtour.application.command.beer.UpdateBeerSrmCommand;
import org.garred.brewtour.application.command.beer.UpdateBeerStyleCommand;
import org.garred.brewtour.domain.AvailableImages;
import org.junit.Test;

public class BeerCommandSerializationTest extends AbstractSerializationTest {

	public BeerCommandSerializationTest() {
		super("command/beer", "org.garred.brewtour.application.command.beer", COMMAND);
	}

	@Test
	public void testAddBeer() {
		reflectionValidate(new AddBeerCommand(BEER_NAME, LOCATION_ID_2, BREWERY_NAME));
	}
	@Test
	public void testUpdateBeerDescription() {
		reflectionValidate(new UpdateBeerDescriptionCommand(BEER_ID, BEER_DESCRIPTION));
	}
	@Test
	public void testUpdateBeerStyle() {
		reflectionValidate(new UpdateBeerStyleCommand(BEER_ID, BEER_STYLE));
	}
	@Test
	public void testUpdateBeerAbv() {
		reflectionValidate(new UpdateBeerAbvCommand(BEER_ID, BEER_ABV));
	}
	@Test
	public void testUpdateBeerIbu() {
		reflectionValidate(new UpdateBeerIbuCommand(BEER_ID, BEER_IBU));
	}
	@Test
	public void testUpdateBeerSrm() {
		reflectionValidate(new UpdateBeerSrmCommand(BEER_ID, BEER_SRM));
	}

	@Test
	public void testUpdateBeerProfessionalRating() {
		reflectionValidate(new UpdateBeerProfessionalRatingCommand(BEER_ID, RATE_BEER, "http://www.ratebeer.com/beer/fremont-universale-pale-ale/104575/", new BigDecimal(89), new BigDecimal(100)));
	}

	@Test
	public void testAddBeerReview() {
		reflectionValidate(new AddBeerReviewCommand(BEER_ID, GOLD, BEER_REVIEW));
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
