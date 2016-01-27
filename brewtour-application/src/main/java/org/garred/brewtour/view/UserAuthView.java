package org.garred.brewtour.view;

import java.util.Set;

import org.garred.brewtour.application.Entity;
import org.garred.brewtour.application.UserId;
import org.garred.brewtour.application.event.UserAddedEvent;
import org.garred.brewtour.security.UserAuth;

public class UserAuthView implements Entity<UserId>,UserAuth {

	public static final String ADMIN_ROLE = "org.garred.brewtour.roles.ADMIN";
	public static final String TEST_ROLE = "org.garred.brewtour.roles.TEST";

	public UserId userId;
	public String login;
	public Set<String> roles;

	@Override
	public UserId identifier() {
		return this.userId;
	}

	public static UserAuthView fromEvent(UserAddedEvent event) {
		final UserAuthView view = new UserAuthView();
		view.userId = event.userId;
		view.login = event.login;
		return view;
	}

}
