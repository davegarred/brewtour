package org.garred.brewtour.application.event;

import org.garred.brewtour.application.UserId;

public abstract class AbstractUserEvent {

	public final UserId userId;

	public AbstractUserEvent(UserId userId) {
		this.userId = userId;
	}

}
