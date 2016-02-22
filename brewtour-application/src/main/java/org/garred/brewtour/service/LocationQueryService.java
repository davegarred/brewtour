package org.garred.brewtour.service;

import org.garred.brewtour.domain.LocaleId;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.view.LocaleView;
import org.garred.brewtour.view.LocationBeersCombinedView;
import org.garred.brewtour.view.LocationView;

public interface LocationQueryService extends LocationService {

	LocationView getLocation(LocationId locationId);
	LocationBeersCombinedView getLocationDetails(LocationId locationId);
	LocaleView getLocale(LocaleId seattle);

}
