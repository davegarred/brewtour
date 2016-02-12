package org.garred.brewtour.service;

import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.view.UserAuthView;

public interface UserAuthService {

	UserAuthView getCurrentUserAuth();
	UserAuthView getUserAuth(UserId userId);
	UserAuthView findUserByLogin(String login);

}
