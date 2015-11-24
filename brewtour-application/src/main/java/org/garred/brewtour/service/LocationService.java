package org.garred.brewtour.service;

import org.garred.brewtour.api.AddBeer;
import org.garred.brewtour.api.BeerAvailable;
import org.garred.brewtour.api.BeerUnavailable;
import org.garred.brewtour.api.ModifyBeer;
import org.garred.brewtour.api.ModifyLocationDescription;

public interface LocationService {

	void addBeer(AddBeer addBeer);
	void modifyBeer(ModifyBeer modifyBeer);
	void beerAvailable(BeerAvailable beerAvailable);
	void beerUnavailable(BeerUnavailable beerUnavailable);

	void modifyLocationDescription(ModifyLocationDescription modifyDescription);
}
