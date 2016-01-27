package org.garred.brewtour.view;

import java.util.HashSet;
import java.util.Set;

import org.garred.brewtour.application.Entity;
import org.garred.brewtour.application.LocationId;
import org.garred.brewtour.application.UserId;
import org.garred.brewtour.application.event.UserAddedEvent;

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
