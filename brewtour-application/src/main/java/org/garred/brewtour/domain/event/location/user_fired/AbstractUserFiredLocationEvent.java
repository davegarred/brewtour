package org.garred.brewtour.domain.event.location.user_fired;

import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.domain.event.location.AbstractLocationEvent;

public abstract class AbstractUserFiredLocationEvent extends AbstractLocationEvent {

	public final UserId userId;
	public final String userScreenName;

	public AbstractUserFiredLocationEvent(LocationId locationId, UserId userId, String userScreenName) {
		super(locationId);
		this.userId = userId;
		this.userScreenName = userScreenName;
	}

}
