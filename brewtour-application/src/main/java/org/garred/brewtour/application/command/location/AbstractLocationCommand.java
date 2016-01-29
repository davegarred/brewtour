package org.garred.brewtour.application.command.location;

import org.garred.brewtour.application.command.AggregateCommand;
import org.garred.brewtour.domain.LocationId;

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
