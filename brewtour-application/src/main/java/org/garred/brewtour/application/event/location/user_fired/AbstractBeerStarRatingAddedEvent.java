package org.garred.brewtour.application.event.location.user_fired;

import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.ReviewMedal;
import org.garred.brewtour.domain.UserId;

public abstract class AbstractBeerStarRatingAddedEvent extends AbstractUserFiredLocationEvent {

	public final String name;
	public final ReviewMedal medal;

	public AbstractBeerStarRatingAddedEvent(LocationId locationId, UserId userId, String name, ReviewMedal medal) {
		super(locationId, userId);
		this.name = name;
		this.medal = medal;
	}

}
