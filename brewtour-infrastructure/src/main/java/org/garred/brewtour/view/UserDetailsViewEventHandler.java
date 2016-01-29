package org.garred.brewtour.view;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.garred.brewtour.application.event.user.FavoriteLocationsUpdatedEvent;
import org.garred.brewtour.application.event.user.UserAddedEvent;
import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.repository.UserDetailsViewRepository;

public class UserDetailsViewEventHandler extends AbstractViewEventHandler<UserId, UserDetailsView> {

	public UserDetailsViewEventHandler(UserDetailsViewRepository repository) {
		super(repository);
	}

	@EventHandler
	public void on(UserAddedEvent event) {
		this.repository.save(UserDetailsView.fromEvent(event));
	}
	@EventHandler
	public void on(FavoriteLocationsUpdatedEvent event) {
		update(event.userId, v -> v.favoriteLocations = event.favoriteLocations);
	}

}
