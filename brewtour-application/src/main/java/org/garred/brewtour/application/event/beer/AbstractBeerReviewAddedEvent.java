package org.garred.brewtour.application.event.beer;

import java.time.LocalDateTime;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.ReviewMedal;
import org.garred.brewtour.domain.UserId;

public abstract class AbstractBeerReviewAddedEvent extends AbstractBeerStarRatingAddedEvent {

	public final LocalDateTime time;
	public final String review;

	public AbstractBeerReviewAddedEvent(BeerId beerId, UserId userId, String userScreenName, ReviewMedal medal, LocalDateTime time, String review) {
		super(beerId, userId, userScreenName, medal);
		this.time = time;
		this.review = review;
	}

}
