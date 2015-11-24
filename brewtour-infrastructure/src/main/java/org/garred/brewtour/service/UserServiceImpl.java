package org.garred.brewtour.service;

import java.util.HashSet;
import java.util.Set;

import org.garred.brewtour.api.AddFavoriteLocation;
import org.garred.brewtour.api.RemoveFavoriteLocation;
import org.garred.brewtour.application.LocationId;
import org.garred.brewtour.application.UserDetails;
import org.garred.brewtour.application.UserId;
import org.garred.brewtour.repository.ObjectDoesNotExistException;
import org.garred.brewtour.repository.UserDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	private final UserDetailsRepository userRepo;

	public UserServiceImpl(UserDetailsRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails getDetails(UserId userId) {
		if(userId == null) {
			return null;
		}
		return this.userRepo.get(userId);
	}

	@Override
	public UserDetails discoverUser(UserId userId, UserId previousUserId, boolean testUser, boolean admin) {
		if(userId == null) {
			return null;
		}
		final UserDetails previousUser = this.userRepo.get(previousUserId);
		final Set<LocationId> favorites = previousUser == null ? new HashSet<>() : previousUser.getFavoriteLocations();
		final UserDetails details = new UserDetails(userId, favorites, testUser, admin);
		this.userRepo.save(details);
		return details;
	}
	@Override
	public UserDetails addFavorite(UserId userId, AddFavoriteLocation location) {
		if(userId == null) {
			return null;
		}
		UserDetails user = this.userRepo.get(userId);
		if(user == null) {
			final HashSet<LocationId> locations = new HashSet<LocationId>(1);
			locations.add(location.locationId);
			user = UserDetails.userDetails(userId, locations);
			this.userRepo.save(user);
		} else {
			user.getFavoriteLocations().add(location.locationId);
			try {
				this.userRepo.update(user);
			} catch (final ObjectDoesNotExistException e) {
				LOGGER.error("Attempt to add favorite location to a user that was found but no longer exists", e);
			}
		}
		return user;
	}

	@Override
	public UserDetails removeFavorite(UserId userId, RemoveFavoriteLocation location) {
		if(userId == null) {
			return null;
		}
		final UserDetails user = this.userRepo.get(userId);
		user.getFavoriteLocations().remove(location.locationId);
		try {
			this.userRepo.update(user);
		} catch (final ObjectDoesNotExistException e) {
			LOGGER.error("Attempt to remove favorite location from a user that does not exist", e);
		}
		return user;
	}

}
