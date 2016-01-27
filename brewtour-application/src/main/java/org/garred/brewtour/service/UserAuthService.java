package org.garred.brewtour.service;

import org.garred.brewtour.application.UserId;
import org.garred.brewtour.view.UserAuthView;

public interface UserAuthService {

	UserAuthView getCurrentUserAuth();
	UserAuthView getUserAuth(UserId userId);

}
