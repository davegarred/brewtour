package org.garred.brewtour.service;

import static java.util.Collections.singleton;

import java.util.Set;

import org.garred.brewtour.application.UserAuth;
import org.garred.brewtour.application.UserId;
import org.garred.brewtour.repository.UserAuthRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserAuthServiceImpl implements UserAuthService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthServiceImpl.class);

	private final UserAuthRepository userRepo;

	public UserAuthServiceImpl(UserAuthRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public UserAuth getUserAuth(UserId userId) {
		if(userId == null) {
			return null;
		}
		return this.userRepo.get(userId);
	}

	@Override
	public UserAuth discoverUser(String login, UserId userId, Set<String> roles) {
		if(login == null) {
			return null;
		}
		final UserAuth details = new UserAuth(userId, login, roles);
		this.userRepo.save(details);
		LOGGER.info("User {} discovered", login);
		return details;
	}

	@Override
	public UserAuth discoverUser(String login, UserId userId, String role) {
		return discoverUser(login, userId, singleton(role));
	}

}
