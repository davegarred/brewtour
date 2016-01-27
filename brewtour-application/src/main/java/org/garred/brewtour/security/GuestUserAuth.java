package org.garred.brewtour.security;

import java.util.UUID;

import org.garred.brewtour.application.UserId;

public class GuestUserAuth implements UserAuth {

	private final UserId userId;

	public GuestUserAuth(UserId userId) {
		this.userId = userId;
	}

	@Override
	public UserId identifier() {
		return this.userId;
	}

	public static UserAuth randomGuestAuth() {
		return new GuestUserAuth(new UserId(UUID.randomUUID().toString()));
	}

}
