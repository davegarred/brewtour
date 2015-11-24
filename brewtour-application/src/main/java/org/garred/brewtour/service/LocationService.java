package org.garred.brewtour.service;

import org.garred.brewtour.api.AddBeer;
import org.garred.brewtour.api.BeerAvailable;
import org.garred.brewtour.api.BeerUnavailable;
import org.garred.brewtour.api.ModifyBeer;
import org.garred.brewtour.api.ModifyLocationDescription;
import org.garred.brewtour.application.Locale;
import org.garred.brewtour.application.LocaleId;
import org.garred.brewtour.application.Location;
import org.garred.brewtour.application.LocationId;

public interface LocationService {

	void addBeer(AddBeer addBeer);
	void modifyBeer(ModifyBeer modifyBeer);
	void beerAvailable(BeerAvailable beerAvailable);
	void beerUnavailable(BeerUnavailable beerUnavailable);

	void modifyLocationDescription(ModifyLocationDescription modifyDescription);

	Location getLocation(LocationId locationId);
	Locale getLocale(LocaleId seattle);

}
