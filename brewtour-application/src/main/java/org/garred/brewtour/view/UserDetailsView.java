package org.garred.brewtour.view;

import java.util.HashMap;
import java.util.Map;

import org.garred.brewtour.application.event.user.UserAddedEvent;
import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.Entity;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.ReviewMedal;
import org.garred.brewtour.domain.UserId;

public class UserDetailsView extends AbstractView implements Entity<UserId> {

	public UserId userId;
	public String screenName;
	public String login;
	public Map<LocationId,Review> locationReviews;
	public Map<BeerId,Review> beerReviews;

	@Override
	public UserId identifier() {
		return this.userId;
	}

	public void addLocationReview(LocationId locationId, String userScreenName, ReviewMedal medal, String review) {
		this.locationReviews.put(locationId, new Review(userScreenName, medal.name(), review));
	}
	public void addBeerReview(BeerId beerId, String userScreenName, ReviewMedal medal, String review) {
		this.beerReviews.put(beerId, new Review(userScreenName, medal.name(), review));
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
