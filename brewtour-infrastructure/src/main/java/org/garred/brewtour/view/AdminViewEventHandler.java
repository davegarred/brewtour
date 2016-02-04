package org.garred.brewtour.view;

import static org.garred.brewtour.domain.LocaleId.SEATTLE;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.garred.brewtour.application.event.location.user_fired.LocationCommentAddedEvent;
import org.garred.brewtour.domain.LocaleId;
import org.garred.brewtour.repository.AdminViewRepository;
import org.garred.brewtour.view.AdminView.CommentView;

public class AdminViewEventHandler extends AbstractViewEventHandler<LocaleId, AdminView> {

	public AdminViewEventHandler(AdminViewRepository repository) {
		super(repository);
	}

	@EventHandler
	public void on(LocationCommentAddedEvent event) {
		final AdminView locale = getOrInitialize();
		locale.comments.add(CommentView.newComment(event.locationId, event.userId, event.comment, event.time));
		persist(locale);
	}

	private AdminView getOrInitialize() {
		AdminView view = this.repository.get(SEATTLE);
		if(view == null) {
			view = new AdminView();
			view.id = SEATTLE;
			this.repository.save(view);
		}
		return view;
	}

}
