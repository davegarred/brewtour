package org.garred.brewtour.service;

import org.garred.brewtour.application.LocaleId;
import org.garred.brewtour.application.LocationId;
import org.garred.brewtour.view.LocaleView;
import org.garred.brewtour.view.LocationView;

public interface LocationQueryService extends LocationService {

	LocationView getLocation(LocationId locationId);
	LocaleView getLocale(LocaleId seattle);

}
