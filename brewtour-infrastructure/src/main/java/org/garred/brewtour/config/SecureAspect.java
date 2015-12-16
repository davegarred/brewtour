package org.garred.brewtour.config;

import static java.util.Arrays.asList;
import static org.garred.brewtour.application.UserAuth.ADMIN_ROLE;
import static org.garred.brewtour.application.UserAuth.TEST_ROLE;

import java.util.Collection;
import java.util.HashSet;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.garred.brewtour.application.UserAuth;

@Aspect
public class SecureAspect {

	private static final Collection<String> REQUIRED_ROLES = new HashSet<>(asList(ADMIN_ROLE, TEST_ROLE));

	@Before("@annotation(org.garred.brewtour.config.Secure)")
	public void secure() {
		final UserAuth user = UserHandler.get();
		if(user == null || !(user.hasAnyRole(REQUIRED_ROLES))) {
			throw new RuntimeException("Attempt to modify an object without permission");
		}
	}
}
