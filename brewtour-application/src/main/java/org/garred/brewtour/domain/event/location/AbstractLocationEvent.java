package org.garred.brewtour.domain.event.location;

import org.garred.brewtour.domain.LocationId;

public abstract class AbstractLocationEvent {

	public final LocationId locationId;

	public AbstractLocationEvent(LocationId locationId) {
		this.locationId = locationId;
	}

}
