package org.garred.brewtour.config;

import static java.util.Arrays.asList;
import static org.garred.brewtour.view.UserAuthView.ADMIN_ROLE;
import static org.garred.brewtour.view.UserAuthView.TEST_ROLE;

import java.util.Collection;
import java.util.HashSet;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.garred.brewtour.security.UserHolder;
import org.garred.brewtour.view.UserAuthView;

@Aspect
public class SecureAspect {

	private static final Collection<String> REQUIRED_ROLES = new HashSet<>(asList(ADMIN_ROLE, TEST_ROLE));

	@Before("@annotation(org.garred.brewtour.security.Secure)")
	public void secure() {
		final UserAuthView user = UserHolder.getAuthenticated();
		if(user == null || !hasAnyRole(REQUIRED_ROLES, user)) {
			throw new RuntimeException("Attempt to modify an object without permission");
		}
	}

	private static boolean hasAnyRole(Collection<String> requiredRoles, UserAuthView user) {
		for(final String role : user.roles) {
			if(requiredRoles.contains(role)) {
				return true;
			}
		}
		return false;
	}
}
