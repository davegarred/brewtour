package org.garred.brewtour.service;

import org.garred.brewtour.domain.UserId;

public interface UserCommandHandlerService {

	UserId randomUserId();
	UserId lastUserId();

}
