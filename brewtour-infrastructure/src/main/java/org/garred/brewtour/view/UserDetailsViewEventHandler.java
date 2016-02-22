package org.garred.brewtour.view;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.garred.brewtour.application.event.beer.BeerReviewAddedByUserEvent;
import org.garred.brewtour.application.event.beer.BeerStarRatingAddedByUserEvent;
import org.garred.brewtour.application.event.location.user_fired.LocationReviewAddedByUserEvent;
import org.garred.brewtour.application.event.location.user_fired.LocationStarRatingAddedByUserEvent;
import org.garred.brewtour.application.event.user.FavoriteBeersUpdatedEvent;
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
	public void on(LocationReviewAddedByUserEvent event) {
		update(event.userId, v -> v.addLocationReview(event.locationId, event.userScreenName, event.medal, event.review));
	}
	@EventHandler
	public void on(LocationStarRatingAddedByUserEvent event) {
		update(event.userId, v -> v.addLocationReview(event.locationId, event.userScreenName, event.medal, null));
	}
	@EventHandler
	public void on(BeerReviewAddedByUserEvent event) {
		update(event.userId, v -> v.addBeerReview(event.beerId, event.userScreenName,event.medal, event.review));
	}
	@EventHandler
	public void on(BeerStarRatingAddedByUserEvent event) {
		update(event.userId, v -> v.addBeerReview(event.beerId, event.userScreenName, event.medal, null));
	}

	@EventHandler
	public void on(FavoriteLocationsUpdatedEvent event) {
		update(event.userId, v -> v.favoriteLocations = event.favoriteLocations);
	}
	@EventHandler
	public void on(FavoriteBeersUpdatedEvent event) {
		update(event.userId, v -> v.favoriteBeers = event.favoriteBeers);
	}

}
