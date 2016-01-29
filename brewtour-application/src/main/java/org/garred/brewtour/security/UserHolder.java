package org.garred.brewtour.security;

import static java.lang.Boolean.TRUE;

import org.garred.brewtour.view.UserAuthView;

public class UserHolder {

	private static ThreadLocal<UserAuth> tl = new ThreadLocal<>();
	private static ThreadLocal<Boolean> tlBool = new ThreadLocal<>();

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

	public static UserAuth clear() {
		final UserAuth updated = TRUE.equals(tlBool.get()) ? tl.get() : null;
		tl.remove();
		tlBool.remove();
		return updated;
	}

	public static void update(UserAuthView userAuth) {
		set(userAuth);
		tlBool.set(TRUE);
	}



}
