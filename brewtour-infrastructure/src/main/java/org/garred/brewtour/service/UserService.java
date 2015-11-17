package org.garred.brewtour.service;

import java.util.HashSet;

import org.garred.brewtour.application.LocationId;
import org.garred.brewtour.application.UserDetails;
import org.garred.brewtour.application.UserId;
import org.garred.brewtour.repository.UserDetailsRepository;

public class UserService {

	private final UserDetailsRepository userRepo;
	
	public UserService(UserDetailsRepository userRepo) {
		this.userRepo = userRepo;
	}

	public UserDetails getDetails(UserId userId) {
		return userRepo.get(userId);
	}
	
	public UserDetails addFavorite(UserId userId, LocationId locationId) {
		UserDetails user = userRepo.get(userId);
		if(user == null) {
			HashSet<LocationId> locations = new HashSet<LocationId>(1);
			locations.add(locationId);
			user = new UserDetails(userId, locations);
			userRepo.save(userId, user);
		} else {
			user.getFavoriteLocations().add(locationId);
			userRepo.update(userId, user);
		}
		return user;
	}

	public UserDetails removeFavorite(UserId userId, LocationId locationId) {
		UserDetails user = userRepo.get(userId);
		user.getFavoriteLocations().remove(locationId);
		return user;
	}
	
}
