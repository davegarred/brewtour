package org.garred.brewtour.application.event.location.user_fired;

import java.time.LocalDateTime;

import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.UserId;

public abstract class AbstractLocationReviewAddedEvent extends AbstractLocationStarRatingAddedEvent {

	public final LocalDateTime time;
	public final String review;

	public AbstractLocationReviewAddedEvent(LocationId locationId, UserId userId, LocalDateTime time, int stars, String review) {
		super(locationId, userId, stars);
		this.time = time;
		this.review = review;
	}

}
