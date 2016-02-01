package org.garred.brewtour.application.event.location;

import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.Review;

public abstract class AbstractBeerReviewAddedEvent extends AbstractLocationEvent {

	public final String name;
	public final Review review;

	public AbstractBeerReviewAddedEvent(LocationId locationId, String name, Review review) {
		super(locationId);
		this.name = name;
		this.review = review;
	}

}
