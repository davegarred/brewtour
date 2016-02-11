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
			@JsonProperty("userScreenName") String userScreenName,
			@JsonProperty("medal") ReviewMedal medal) {
		super(locationId, userId, userScreenName, medal);
	}

	public static LocationStarRatingAddedByUserEvent fromCommand(AddLocationRatingCommand command, UserId userId, String userScreenName) {
		return new LocationStarRatingAddedByUserEvent(command.locationId, userId, userScreenName, command.medal);
	}

}
