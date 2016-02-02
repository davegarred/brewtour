package org.garred.brewtour.application.event.location.user_fired;

import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.UserId;

import com.fasterxml.jackson.annotation.JsonCreator;

public abstract class AbstractLocationStarRatingAddedEvent extends AbstractUserFiredLocationEvent {

	public final int stars;

	@JsonCreator
	public AbstractLocationStarRatingAddedEvent(LocationId locationId, UserId userId, int stars) {
		super(locationId, userId);
		this.stars = stars;
	}

}
