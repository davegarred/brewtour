package org.garred.brewtour.application.event.location.user_fired;

import org.garred.brewtour.application.event.location.AbstractLocationEvent;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.UserId;

public abstract class AbstractUserFiredLocationEvent extends AbstractLocationEvent {

	public final UserId userId;

	public AbstractUserFiredLocationEvent(LocationId locationId, UserId userId) {
		super(locationId);
		this.userId = userId;
	}

}
