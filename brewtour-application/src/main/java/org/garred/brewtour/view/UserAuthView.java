package org.garred.brewtour.view;

import java.util.Collection;
import java.util.Set;

import org.garred.brewtour.application.event.user.UserAddedEvent;
import org.garred.brewtour.domain.Entity;
import org.garred.brewtour.domain.Hash;
import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.security.UserAuth;

public class UserAuthView extends AbstractView implements Entity<UserId>,UserAuth {

	public static final String ADMIN_ROLE = "org.garred.brewtour.roles.ADMIN";
	public static final String TEST_ROLE = "org.garred.brewtour.roles.TEST";

	public UserId userId;
	public String login;
	public Hash hash;
	public Set<String> roles;

	@Override
	public UserId identifier() {
		return this.userId;
	}

	@Override
	public boolean identified() {
		return true;
	}

	@Override
	public Collection<String> roles() {
		return this.roles;
	}

	public static UserAuthView fromEvent(UserAddedEvent event) {
		final UserAuthView view = new UserAuthView();
		view.userId = event.userId;
		view.login = event.login;
		view.hash = event.hash;
		return view;
	}

}
