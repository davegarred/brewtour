package org.garred.brewtour.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.garred.brewtour.application.UserDetails;
import org.garred.brewtour.application.UserId;
import org.garred.brewtour.repository.UserDetailsRepository;

@Aspect
public class SecureAspect {

	private final UserDetailsRepository userRepo;

	public SecureAspect(UserDetailsRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Before("@annotation(org.garred.brewtour.config.Secure)")
	public void secure() {
		final UserId userId = UserHandler.get();
		final UserDetails user = userId == null ? null : this.userRepo.get(userId);
		if(user == null || !(user.isAdmin() || user.isTestUser())) {
			throw new RuntimeException("Attempt to modify an object without permission");
		}
	}
}
