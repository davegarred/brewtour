package org.garred.brewtour.view;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.garred.brewtour.application.event.location.user_fired.AbstractBeerReviewAddedEvent;
import org.garred.brewtour.application.event.location.user_fired.AbstractBeerStarRatingAddedEvent;
import org.garred.brewtour.application.event.location.user_fired.AbstractLocationReviewAddedEvent;
import org.garred.brewtour.application.event.location.user_fired.AbstractLocationStarRatingAddedEvent;
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
	public void on(AbstractLocationReviewAddedEvent event) {
		update(event.userId, v -> v.addLocationReview(event.locationId, event.userScreenName, event.medal, event.review));
	}
	@EventHandler
	public void on(AbstractLocationStarRatingAddedEvent event) {
		update(event.userId, v -> v.addLocationReview(event.locationId, event.userScreenName, event.medal, null));
	}
	@EventHandler
	public void on(AbstractBeerReviewAddedEvent event) {
		update(event.userId, v -> v.addBeerReview(event.locationId, event.beerName, event.userScreenName,event.medal, event.review));
	}
	@EventHandler
	public void on(AbstractBeerStarRatingAddedEvent event) {
		update(event.userId, v -> v.addBeerReview(event.locationId, event.beerName, event.userScreenName, event.medal, null));
	}

}
