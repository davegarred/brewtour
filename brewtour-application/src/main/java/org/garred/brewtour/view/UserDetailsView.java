package org.garred.brewtour.view;

import java.util.HashSet;
import java.util.Set;

import org.garred.brewtour.application.event.user.UserAddedEvent;
import org.garred.brewtour.domain.Entity;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.UserId;

public class UserDetailsView implements Entity<UserId> {

	public UserId userId;
	public String login;
	public Set<LocationId> favoriteLocations;

	@Override
	public UserId identifier() {
		return this.userId;
	}

	public static UserDetailsView fromEvent(UserAddedEvent event) {
		final UserDetailsView view = new UserDetailsView();
		view.userId = event.userId;
		view.login = event.login;
		view.favoriteLocations = new HashSet<>();
		return view;
	}

}
