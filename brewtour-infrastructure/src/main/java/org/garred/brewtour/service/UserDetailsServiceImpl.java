package org.garred.brewtour.service;

import static org.garred.brewtour.domain.Hash.hashFromPassword;

import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.repository.UserAuthViewRepository;
import org.garred.brewtour.repository.UserDetailsViewRepository;
import org.garred.brewtour.security.UserHolder;
import org.garred.brewtour.view.UserAuthView;
import org.garred.brewtour.view.UserDetailsView;

public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserDetailsViewRepository userRepo;
	private final UserAuthViewRepository userAuthRepo;

	public UserDetailsServiceImpl(UserDetailsViewRepository userRepo,
			UserAuthViewRepository userAuthRepo) {
		this.userRepo = userRepo;
		this.userAuthRepo = userAuthRepo;
	}

	@Override
	public UserDetailsView getCurrentUserDetails() {
		return getDetails(UserHolder.get().identifier());
	}

	@Override
	public UserDetailsView getDetails(UserId userId) {
		if(userId == null) {
			return null;
		}
		return this.userRepo.get(userId);
	}

	@Override
	public UserAuthView login(String login, String password) {
		final UserAuthView auth = this.userAuthRepo.findByLogin(login);
		if(auth == null || !auth.hash.equals(hashFromPassword(auth.userId, password))) {
			return null;
		}
		return auth;
	}

}
