package org.garred.brewtour.security;

import org.garred.brewtour.view.UserAuthView;

public class UserHolder {

	private static ThreadLocal<UserAuth> tl = new ThreadLocal<>();

	public static boolean isAuthenticated() {
		return tl.get() instanceof UserAuthView;
	}

	public static void set(UserAuth user) {
		tl.set(user);
	}

	public static UserAuthView getAuthenticated() {
		if(tl.get() instanceof UserAuthView) {
			return (UserAuthView) tl.get();
		}
		return null;
	}

	public static UserAuth get() {
		return tl.get();
	}

	public static void clear() {
		tl.remove();
	}



}
