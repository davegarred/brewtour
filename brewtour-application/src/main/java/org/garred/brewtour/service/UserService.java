package org.garred.brewtour.service;

import org.garred.brewtour.api.AddFavoriteLocation;
import org.garred.brewtour.api.RemoveFavoriteLocation;
import org.garred.brewtour.application.UserDetails;
import org.garred.brewtour.application.UserId;

public interface UserService {

	UserDetails discoverUser(UserId userId, UserId previousUserId, boolean testUser, boolean admin);

	UserDetails getDetails(UserId userId);

	UserDetails addFavorite(UserId userId, AddFavoriteLocation addFavoriteLocation);

	UserDetails removeFavorite(UserId userId, RemoveFavoriteLocation removeFavoriteLocation);

}
