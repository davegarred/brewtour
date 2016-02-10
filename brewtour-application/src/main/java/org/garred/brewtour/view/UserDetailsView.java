package org.garred.brewtour.view;

import java.util.HashMap;
import java.util.Map;

import org.garred.brewtour.application.event.user.UserAddedEvent;
import org.garred.brewtour.domain.Entity;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.UserId;

public class UserDetailsView extends AbstractView implements Entity<UserId> {

	public UserId userId;
	public String screenName;
	public String login;
	public Map<LocationId,Review> locationReviews;
	public Map<LocationId,Map<String,Review>> beerReviews;

	@Override
	public UserId identifier() {
		return this.userId;
	}

	public void addLocationReview(LocationId locationId, int stars, String review) {
		this.locationReviews.put(locationId, new Review(stars,review));
	}
	public void addBeerReview(LocationId locationId, String beerName, int stars, String review) {
		Map<String, Review> location = this.beerReviews.get(locationId);
		if(location == null) {
			location = new HashMap<>();
			this.beerReviews.put(locationId, location);
		}
		location.put(beerName, new Review(stars,review));
	}

	public static UserDetailsView fromEvent(UserAddedEvent event) {
		final UserDetailsView view = new UserDetailsView();
		view.userId = event.userId;
		view.screenName = event.screenName;
		view.login = event.login;
		view.locationReviews = new HashMap<>();
		view.beerReviews = new HashMap<>();
		return view;
	}

}
