package org.garred.brewtour.application.event;

import java.math.BigDecimal;

import org.garred.brewtour.application.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationRatingUpdatedEvent extends AbstractLocationEvent {

	public final BigDecimal description;

	@JsonCreator
	public LocationRatingUpdatedEvent(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("rating") BigDecimal rating) {
		super(locationId);
		this.description = rating;
	}

}
