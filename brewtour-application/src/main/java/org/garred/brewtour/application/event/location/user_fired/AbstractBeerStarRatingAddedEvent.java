package org.garred.brewtour.application.event.location.user_fired;

import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.ReviewMedal;
import org.garred.brewtour.domain.UserId;

public abstract class AbstractBeerStarRatingAddedEvent extends AbstractUserFiredLocationEvent {

	public final String beerName;
	public final ReviewMedal medal;

	public AbstractBeerStarRatingAddedEvent(LocationId locationId, UserId userId, String userScreenName, String beerName, ReviewMedal medal) {
		super(locationId, userId, userScreenName);
		this.beerName = beerName;
		this.medal = medal;
	}

}
