package org.garred.brewtour.service;

import java.util.UUID;

import org.garred.brewtour.domain.UserId;

public class UserCommandHandlerServiceImpl implements UserCommandHandlerService {

	private final Object lock = new Object();
	private UserId userId;

	@Override
	public UserId randomUserId() {
		synchronized(this.lock) {
			this.userId = new UserId(UUID.randomUUID().toString());
			return this.userId;
		}
	}

	@Override
	public UserId lastUserId() {
		synchronized(this.lock) {
			return this.userId;
		}
	}


}
