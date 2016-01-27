package org.garred.brewtour.application.event;

import org.garred.brewtour.application.LocationId;

public abstract class AbstractLocationEvent {

	public final LocationId locationId;

	public AbstractLocationEvent(LocationId locationId) {
		this.locationId = locationId;
	}

}
