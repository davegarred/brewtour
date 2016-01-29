package org.garred.brewtour.application.event.location;

import org.garred.brewtour.application.command.location.AddLocationReviewCommand;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.Review;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationReviewAddedEvent extends AbstractLocationEvent {

	public final Review review;

	@JsonCreator
	public LocationReviewAddedEvent(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("review") Review review) {
		super(locationId);
		this.review = review;
	}

	public static LocationReviewAddedEvent fromCommand(AddLocationReviewCommand command) {
		return new LocationReviewAddedEvent(command.locationId, command.review);
	}

}
