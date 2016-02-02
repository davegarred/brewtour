package org.garred.brewtour.application.event.location.user_fired;

import org.garred.brewtour.application.command.location.AddBeerRatingCommand;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.UserId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerStarRatingAddedByUserEvent extends AbstractBeerStarRatingAddedEvent {

	@JsonCreator
	public BeerStarRatingAddedByUserEvent(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("userId") UserId userId,
			@JsonProperty("name") String name,
			@JsonProperty("stars") int stars) {
		super(locationId, userId, name, stars);
	}

	public static BeerStarRatingAddedByUserEvent fromCommand(AddBeerRatingCommand command, UserId userId) {
		return new BeerStarRatingAddedByUserEvent(command.locationId, userId, command.name, command.stars);
	}

}
