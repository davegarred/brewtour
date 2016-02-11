package org.garred.brewtour.application.event.location.user_fired;

import org.garred.brewtour.application.event.location.AbstractLocationEvent;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.ReviewMedal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerRatingUpdatedEvent extends AbstractLocationEvent {

	public final String beerName;
	public final ReviewMedal medal;

	@JsonCreator
	public BeerRatingUpdatedEvent(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("beerName") String beerName,
			@JsonProperty("medal") ReviewMedal medal) {
		super(locationId);
		this.beerName = beerName;
		this.medal = medal;
	}

}
