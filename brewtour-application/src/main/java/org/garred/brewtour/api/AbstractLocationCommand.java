package org.garred.brewtour.api;

import org.garred.brewtour.application.LocationId;

public abstract class AbstractLocationCommand implements AggregateCommand<LocationId> {

	public final LocationId locationId;

	public AbstractLocationCommand(LocationId locationId) {
		this.locationId = locationId;
	}

	@Override
	public LocationId identifier() {
		return this.locationId;
	}

}
