package org.garred.brewtour.application.event.location.user_fired;

import org.garred.brewtour.application.event.location.AbstractLocationEvent;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.ReviewMedal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationRatingUpdatedEvent extends AbstractLocationEvent {

	public final ReviewMedal medal;

	@JsonCreator
	public LocationRatingUpdatedEvent(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("medal") ReviewMedal medal) {
		super(locationId);
		this.medal = medal;
	}

}
