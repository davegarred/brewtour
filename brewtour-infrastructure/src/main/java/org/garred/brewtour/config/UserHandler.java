package org.garred.brewtour.config;

import org.garred.brewtour.application.UserAuth;

public class UserHandler {

	private static ThreadLocal<UserAuth> tl = new ThreadLocal<>();

	public static void set(UserAuth user) {
		tl.set(user);
	}
	public static UserAuth get() {
		return tl.get();
	}
	public static void clear() {
		tl.remove();
	}


}
