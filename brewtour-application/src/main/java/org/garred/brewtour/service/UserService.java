package org.garred.brewtour.service;

import org.garred.brewtour.application.LocationId;
import org.garred.brewtour.application.UserDetails;
import org.garred.brewtour.application.UserId;

public interface UserService {

	UserDetails discoverUser(UserId userId, String login, boolean b);

	UserDetails getDetails(UserId userId);

	UserDetails addFavorite(UserId userId, LocationId locationId);

	UserDetails removeFavorite(UserId userId, LocationId locationId);

}
