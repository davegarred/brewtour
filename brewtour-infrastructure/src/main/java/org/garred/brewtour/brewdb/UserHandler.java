package org.garred.brewtour.brewdb;

import org.garred.brewtour.application.UserId;

public class UserHandler {

	private static ThreadLocal<UserId> tl = new ThreadLocal<>();

	public static void set(UserId userId) {
		tl.set(userId);
	}
	public static UserId get() {
		return tl.get();
	}
	public static void clear() {
		tl.remove();
	}


}
