package org.garred.brewtour.service;

import org.garred.brewtour.application.Locale;
import org.garred.brewtour.application.LocaleId;
import org.garred.brewtour.application.Location;
import org.garred.brewtour.application.LocationId;

public interface LocationQueryService extends LocationService {

	Location getLocation(LocationId locationId);
	Locale getLocale(LocaleId seattle);

}
