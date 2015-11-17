package org.garred.brewtour.service;

import java.util.HashMap;
import java.util.Map;

import org.garred.brewtour.application.UserDetails;
import org.garred.brewtour.application.UserId;
import org.garred.brewtour.repository.UserDetailsRepository;

public class UserDetailsRepositoryStub implements UserDetailsRepository {

	private final Map<UserId,UserDetails> userMap = new HashMap<>();
	
	@Override
	public boolean exists(UserId key) {
		return userMap.containsKey(key);
	}
	@Override
	public UserDetails get(UserId key) {
		return userMap.get(key);
	}

	@Override
	public void save(UserId key, UserDetails value) {
		if(exists(key)) {
			throw new IllegalStateException();
		}
		userMap.put(key, value);
	}

	@Override
	public void update(UserId key, UserDetails value) {
		if(!exists(key)) {
			throw new IllegalStateException();
		}
		userMap.put(key, value);
	}

	@Override
	public void delete(UserId key) {
		userMap.remove(key);
	}

}
