package org.garred.brewtour.application.event.location.user_fired;

import static org.garred.brewtour.security.GuestUserAuth.ANONYMOUS_SCREEN_NAME;

import java.time.LocalDateTime;

import org.garred.brewtour.application.command.location.AddLocationReviewCommand;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.ReviewMedal;
import org.garred.brewtour.domain.UserId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationReviewAddedByAnonymousEvent extends AbstractLocationReviewAddedEvent {

	@JsonCreator
	public LocationReviewAddedByAnonymousEvent(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("userId") UserId userId,
			@JsonProperty("time") LocalDateTime time,
			@JsonProperty("medal") ReviewMedal medal,
			@JsonProperty("review") String review) {
		super(locationId, userId, ANONYMOUS_SCREEN_NAME, time, medal, review);
	}

	public static LocationReviewAddedByAnonymousEvent fromCommand(AddLocationReviewCommand command, UserId userId, LocalDateTime time) {
		return new LocationReviewAddedByAnonymousEvent(command.locationId, userId, time, command.medal, command.review);
	}

}
