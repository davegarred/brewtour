package org.garred.brewtour.application.event.location.user_fired;

import org.garred.brewtour.application.command.location.AddLocationRatingCommand;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.ReviewMedal;
import org.garred.brewtour.domain.UserId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationStarRatingAddedByUserEvent extends AbstractLocationStarRatingAddedEvent {

	@JsonCreator
	public LocationStarRatingAddedByUserEvent(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("userId") UserId userId,
			@JsonProperty("medal") ReviewMedal medal) {
		super(locationId, userId, medal);
	}

	public static LocationStarRatingAddedByUserEvent fromCommand(AddLocationRatingCommand command, UserId userId) {
		return new LocationStarRatingAddedByUserEvent(command.locationId, userId, command.medal);
	}

}
