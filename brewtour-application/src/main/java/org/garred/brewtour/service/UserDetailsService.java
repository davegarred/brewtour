package org.garred.brewtour.service;

import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.view.UserAuthView;
import org.garred.brewtour.view.UserDetailsView;

public interface UserDetailsService {

	UserDetailsView getCurrentUserDetails();
	UserDetailsView getDetails(UserId userId);
	UserAuthView login(String login, String password);

}
