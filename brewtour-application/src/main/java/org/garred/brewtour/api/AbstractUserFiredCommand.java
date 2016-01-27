package org.garred.brewtour.api;

import org.garred.brewtour.application.UserId;
import org.garred.brewtour.security.UserHolder;

public abstract class AbstractUserFiredCommand implements AggregateCommand<UserId> {

	@Override
	public UserId identifier() {
		return UserHolder.get().identifier();
	}

}
