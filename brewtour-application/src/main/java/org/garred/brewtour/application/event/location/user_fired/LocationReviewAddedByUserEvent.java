package org.garred.brewtour.application.event.location.user_fired;

import java.time.LocalDateTime;

import org.garred.brewtour.application.command.location.AddLocationReviewCommand;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.ReviewMedal;
import org.garred.brewtour.domain.UserId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationReviewAddedByUserEvent extends AbstractLocationReviewAddedEvent {

	@JsonCreator
	public LocationReviewAddedByUserEvent(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("userId") UserId userId,
			@JsonProperty("time") LocalDateTime time,
			@JsonProperty("medal") ReviewMedal medal,
			@JsonProperty("review") String review) {
		super(locationId, userId, time, medal, review);
	}

	public static LocationReviewAddedByUserEvent fromCommand(AddLocationReviewCommand command, UserId userId, LocalDateTime time) {
		return new LocationReviewAddedByUserEvent(command.locationId, userId, time, command.medal, command.review);
	}

}