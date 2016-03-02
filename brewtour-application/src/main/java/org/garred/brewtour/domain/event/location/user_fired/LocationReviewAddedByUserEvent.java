package org.garred.brewtour.domain.event.location.user_fired;

import java.time.LocalDateTime;

import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.ReviewMedal;
import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.domain.command.location.AddLocationReviewCommand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationReviewAddedByUserEvent extends AbstractLocationReviewAddedEvent {

	@JsonCreator
	public LocationReviewAddedByUserEvent(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("userId") UserId userId,
			@JsonProperty("userScreenName") String userScreenName,
			@JsonProperty("time") LocalDateTime time,
			@JsonProperty("medal") ReviewMedal medal,
			@JsonProperty("review") String review) {
		super(locationId, userId, userScreenName, time, medal, review);
	}

	public static LocationReviewAddedByUserEvent fromCommand(AddLocationReviewCommand command, UserId userId, String userScreenName, LocalDateTime time) {
		return new LocationReviewAddedByUserEvent(command.locationId, userId, userScreenName, time, command.medal, command.review);
	}

}
