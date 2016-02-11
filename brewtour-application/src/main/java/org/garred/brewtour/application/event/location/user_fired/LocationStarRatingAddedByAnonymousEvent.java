package org.garred.brewtour.application.event.location.user_fired;

import org.garred.brewtour.application.command.location.AddLocationRatingCommand;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.ReviewMedal;
import org.garred.brewtour.domain.UserId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationStarRatingAddedByAnonymousEvent extends AbstractLocationStarRatingAddedEvent {

	@JsonCreator
	public LocationStarRatingAddedByAnonymousEvent(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("userId") UserId userId,
			@JsonProperty("medal") ReviewMedal medal) {
		super(locationId, userId, medal);
	}

	public static LocationStarRatingAddedByAnonymousEvent fromCommand(AddLocationRatingCommand command, UserId userId) {
		return new LocationStarRatingAddedByAnonymousEvent(command.locationId, userId, command.medal);
	}

}
