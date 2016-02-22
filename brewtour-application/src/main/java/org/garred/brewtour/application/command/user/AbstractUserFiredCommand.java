package org.garred.brewtour.application.command.user;

import org.garred.brewtour.application.command.AggregateCommand;
import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.security.UserAuth;
import org.garred.brewtour.security.UserHolder;
import org.garred.brewtour.security.UserNotLoggedInException;

public abstract class AbstractUserFiredCommand implements AggregateCommand<UserId> {

	@Override
	public UserId identifier() {
		final UserAuth auth = UserHolder.get();
		if(auth == null || !auth.identified()) {
			throw new UserNotLoggedInException();
		}
		return auth.identifier();
	}

}
