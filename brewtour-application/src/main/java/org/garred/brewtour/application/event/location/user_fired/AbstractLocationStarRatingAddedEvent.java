package org.garred.brewtour.application.event.location.user_fired;

import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.ReviewMedal;
import org.garred.brewtour.domain.UserId;

import com.fasterxml.jackson.annotation.JsonCreator;

public abstract class AbstractLocationStarRatingAddedEvent extends AbstractUserFiredLocationEvent {

	public final ReviewMedal medal;

	@JsonCreator
	public AbstractLocationStarRatingAddedEvent(LocationId locationId, UserId userId, String userScreenName, ReviewMedal medal) {
		super(locationId, userId, userScreenName);
		this.medal = medal;
	}

}
