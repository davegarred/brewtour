package org.garred.brewtour.service;

import java.util.HashSet;
import java.util.Set;

import org.garred.brewtour.application.LocationId;
import org.garred.brewtour.application.UserDetails;
import org.garred.brewtour.application.UserId;
import org.garred.brewtour.repository.UserDetailsRepository;

public class UserServiceImpl implements UserService {

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

	public UserDetails discoverUser(UserId userId, UserId previousUserId, boolean testUser, boolean admin) {
		if(userId == null) {
			return null;
		}
		final UserDetails previousUser = this.userRepo.get(previousUserId);
		final Set<LocationId> favorites = previousUser == null ? new HashSet<>() : previousUser.getFavoriteLocations();
		final UserDetails details = new UserDetails(userId, favorites, testUser, admin);
		this.userRepo.save(userId, details);
		return details;
	}
	@Override
	public UserDetails addFavorite(UserId userId, LocationId locationId) {
		if(userId == null) {
			return null;
		}
		UserDetails user = this.userRepo.get(userId);
		if(user == null) {
			final HashSet<LocationId> locations = new HashSet<LocationId>(1);
			locations.add(locationId);
			user = UserDetails.userDetails(userId, locations);
			this.userRepo.save(userId, user);
		} else {
			user.getFavoriteLocations().add(locationId);
			this.userRepo.update(userId, user);
		}
		return user;
	}

	@Override
	public UserDetails removeFavorite(UserId userId, LocationId locationId) {
		if(userId == null) {
			return null;
		}
		final UserDetails user = this.userRepo.get(userId);
		user.getFavoriteLocations().remove(locationId);
		this.userRepo.update(userId, user);
		return user;
	}

}
