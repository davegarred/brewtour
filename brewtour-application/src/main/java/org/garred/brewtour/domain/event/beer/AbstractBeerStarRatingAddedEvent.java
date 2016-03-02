package org.garred.brewtour.domain.event.beer;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.ReviewMedal;
import org.garred.brewtour.domain.UserId;

public abstract class AbstractBeerStarRatingAddedEvent extends AbstractUserFiredBeerEvent {

	public final ReviewMedal medal;

	public AbstractBeerStarRatingAddedEvent(BeerId beerId, UserId userId, String userScreenName, ReviewMedal medal) {
		super(beerId, userId, userScreenName);
		this.medal = medal;
	}

}
