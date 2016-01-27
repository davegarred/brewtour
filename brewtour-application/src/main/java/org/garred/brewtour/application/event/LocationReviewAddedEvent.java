package org.garred.brewtour.application.event;

import org.garred.brewtour.api.AddLocationReviewCommand;
import org.garred.brewtour.application.LocationId;
import org.garred.brewtour.application.Review;

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
