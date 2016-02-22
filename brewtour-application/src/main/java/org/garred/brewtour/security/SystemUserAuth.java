package org.garred.brewtour.security;

import static java.util.Collections.singleton;
import static org.garred.brewtour.view.UserAuthView.ADMIN_ROLE;

import java.util.Collection;

import org.garred.brewtour.domain.UserId;

public class SystemUserAuth implements UserAuth {

	public static final SystemUserAuth SYSTEM = new SystemUserAuth();

	private static final String SYSTEM_SCREEN_NAME = "SYSTEM_ADMIN";

	private SystemUserAuth() {}

	@Override
	public UserId identifier() {
		return UserId.SYSTEM;
	}

	@Override
	public String screenName() {
		return SYSTEM_SCREEN_NAME;
	}

	@Override
	public boolean identified() {
		return false;
	}

	@Override
	public Collection<String> roles() {
		return singleton(ADMIN_ROLE);
	}

}
