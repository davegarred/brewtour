package org.garred.brewtour.service;

import org.garred.brewtour.api.AddBeer;
import org.garred.brewtour.api.AddBeerReview;
import org.garred.brewtour.api.AddLocationReview;
import org.garred.brewtour.api.BeerAvailable;
import org.garred.brewtour.api.BeerUnavailable;
import org.garred.brewtour.api.ModifyBeer;
import org.garred.brewtour.api.ModifyLocationDescription;

public interface LocationService {

	void addBeer(AddBeer addBeer);
	void modifyBeer(ModifyBeer modifyBeer);
	void beerAvailable(BeerAvailable beerAvailable);
	void beerUnavailable(BeerUnavailable beerUnavailable);

	void addLocationReview(AddLocationReview locationReview);
	void addBeerReview(AddBeerReview beerReview);

	void modifyLocationDescription(ModifyLocationDescription modifyDescription);

}
