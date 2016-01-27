package org.garred.brewtour.service;

import org.garred.brewtour.application.UserId;
import org.garred.brewtour.view.UserDetailsView;

public interface UserDetailsService {

	UserDetailsView getCurrentUserDetails();
	UserDetailsView getDetails(UserId userId);

}
