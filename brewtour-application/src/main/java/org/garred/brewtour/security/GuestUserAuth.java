package org.garred.brewtour.security;

import static java.util.Collections.emptySet;

import java.util.Collection;
import java.util.UUID;

import org.garred.brewtour.domain.UserId;

public class GuestUserAuth implements UserAuth {

	private final UserId userId;

	public GuestUserAuth(UserId userId) {
		this.userId = userId;
	}

	@Override
	public UserId identifier() {
		return this.userId;
	}

	@Override
	public boolean identified() {
		return false;
	}

	@Override
	public Collection<String> roles() {
		return emptySet();
	}

	public static UserAuth randomGuestAuth() {
		return new GuestUserAuth(new UserId(UUID.randomUUID().toString()));
	}

}
