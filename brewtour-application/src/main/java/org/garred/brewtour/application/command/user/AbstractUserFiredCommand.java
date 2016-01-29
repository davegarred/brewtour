package org.garred.brewtour.application.command.user;

import org.garred.brewtour.application.command.AggregateCommand;
import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.security.UserHolder;

public abstract class AbstractUserFiredCommand implements AggregateCommand<UserId> {

	@Override
	public UserId identifier() {
		return UserHolder.get().identifier();
	}

}
