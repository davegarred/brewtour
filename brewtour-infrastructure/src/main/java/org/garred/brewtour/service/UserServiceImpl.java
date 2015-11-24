package org.garred.brewtour.service;

import java.util.HashSet;

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
		return userRepo.get(userId);
	}

	@Override
	public UserDetails discoverUser(UserId userId, String login, boolean admin) {
		UserDetails details = userRepo.get(userId);
		if(details == null) {
			details = new UserDetails(userId, login, new HashSet<>(), admin);
			userRepo.save(userId, details);
		} else {
			details = details.discover(login, admin);
			userRepo.update(userId, details);
		}
		return details;
	}
	
	@Override
	public UserDetails addFavorite(UserId userId, LocationId locationId) {
		UserDetails user = userRepo.get(userId);
		if(user == null) {
			HashSet<LocationId> locations = new HashSet<LocationId>(1);
			locations.add(locationId);
			user = UserDetails.anonymousUserDetails(userId, locations);
			userRepo.save(userId, user);
		} else {
			user.getFavoriteLocations().add(locationId);
			userRepo.update(userId, user);
		}
		return user;
	}

	@Override
	public UserDetails removeFavorite(UserId userId, LocationId locationId) {
		UserDetails user = userRepo.get(userId);
		user.getFavoriteLocations().remove(locationId);
		userRepo.update(userId, user);
		return user;
	}
	
}
