package org.garred.brewtour.service;

import org.garred.brewtour.application.UserId;
import org.garred.brewtour.repository.UserAuthViewRepository;
import org.garred.brewtour.security.UserHolder;
import org.garred.brewtour.view.UserAuthView;

public class UserAuthServiceImpl implements UserAuthService {

	private final UserAuthViewRepository userRepo;

	public UserAuthServiceImpl(UserAuthViewRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public UserAuthView getUserAuth(UserId userId) {
		if(userId == null) {
			return null;
		}
		return this.userRepo.get(userId);
	}

	@Override
	public UserAuthView getCurrentUserAuth() {
		return getUserAuth(UserHolder.get().identifier());
	}


}
