package org.garred.brewtour.api;

import org.garred.brewtour.application.LocationId;

public class LocationCommand {

	public final LocationId locationId;

	public LocationCommand(LocationId locationId) {
		this.locationId = locationId;
	}

}
