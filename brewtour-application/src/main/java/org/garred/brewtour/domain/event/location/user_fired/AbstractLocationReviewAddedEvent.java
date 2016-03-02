package org.garred.brewtour.domain.event.location.user_fired;

import java.time.LocalDateTime;

import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.ReviewMedal;
import org.garred.brewtour.domain.UserId;

public abstract class AbstractLocationReviewAddedEvent extends AbstractLocationStarRatingAddedEvent {

	public final LocalDateTime time;
	public final String review;

	public AbstractLocationReviewAddedEvent(LocationId locationId, UserId userId, String userScreenName, LocalDateTime time, ReviewMedal medal, String review) {
		super(locationId, userId, userScreenName, medal);
		this.time = time;
		this.review = review;
	}

}
