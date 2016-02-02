package org.garred.brewtour.application.event.location.user_fired;

import java.time.LocalDateTime;

import org.garred.brewtour.application.command.location.AddLocationReviewCommand;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.UserId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationReviewAddedByAnonymousEvent extends AbstractLocationReviewAddedEvent {

	@JsonCreator
	public LocationReviewAddedByAnonymousEvent(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("userId") UserId userId,
			@JsonProperty("time") LocalDateTime time,
			@JsonProperty("stars") int stars,
			@JsonProperty("review") String review) {
		super(locationId, userId, time, stars, review);
	}

	public static LocationReviewAddedByAnonymousEvent fromCommand(AddLocationReviewCommand command, UserId userId, LocalDateTime time) {
		return new LocationReviewAddedByAnonymousEvent(command.locationId, userId, time, command.stars, command.review);
	}

}
