package org.garred.brewtour.domain.event.user;

import org.garred.brewtour.domain.UserId;

public abstract class AbstractUserEvent {

	public final UserId userId;

	public AbstractUserEvent(UserId userId) {
		this.userId = userId;
	}

}
