package org.garred.brewtour.application.event.location.user_fired;

import java.time.LocalDateTime;

import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.ReviewMedal;
import org.garred.brewtour.domain.UserId;

public abstract class AbstractBeerReviewAddedEvent extends AbstractBeerStarRatingAddedEvent {

	public final LocalDateTime time;
	public final String review;

	public AbstractBeerReviewAddedEvent(LocationId locationId, UserId userId, String name, ReviewMedal medal, LocalDateTime time, String review) {
		super(locationId, userId, name, medal);
		this.time = time;
		this.review = review;
	}

}
