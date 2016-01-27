package org.garred.brewtour.service;

import org.garred.brewtour.application.UserId;
import org.garred.brewtour.repository.UserDetailsViewRepository;
import org.garred.brewtour.security.UserHolder;
import org.garred.brewtour.view.UserDetailsView;

public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserDetailsViewRepository userRepo;

	public UserDetailsServiceImpl(UserDetailsViewRepository userRepo) {
		this.userRepo = userRepo;
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

}
