package org.garred.brewtour.view;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.garred.brewtour.application.UserId;
import org.garred.brewtour.application.event.UserAddedEvent;
import org.garred.brewtour.application.event.UserRolesUpdatedEvent;
import org.garred.brewtour.repository.UserAuthViewRepository;

public class UserAuthViewEventHandler extends AbstractViewEventHandler<UserId, UserAuthView> {

	public UserAuthViewEventHandler(UserAuthViewRepository repository) {
		super(repository);
	}

	@EventHandler
	public void on(UserAddedEvent event) {
		this.repository.save(UserAuthView.fromEvent(event));
	}
	@EventHandler
	public void on(UserRolesUpdatedEvent event) {
		update(event.userId, v -> v.roles = event.roles);
	}

}
