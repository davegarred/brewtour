package org.garred.brewtour.domain.command.location;

import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.command.AggregateCommand;

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
