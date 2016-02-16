package org.garred.brewtour.application.event.beer;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.ReviewMedal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerRatingUpdatedEvent extends AbstractBeerEvent {

	public final ReviewMedal medal;

	@JsonCreator
	public BeerRatingUpdatedEvent(@JsonProperty("beerId") BeerId beerId,
			@JsonProperty("medal") ReviewMedal medal) {
		super(beerId);
		this.medal = medal;
	}

}
