package org.garred.brewtour.domain.event.location.user_fired;

import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.ReviewMedal;
import org.garred.brewtour.domain.event.location.AbstractLocationEvent;

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
