package org.garred.brewtour.application.event.location.user_fired;

import org.garred.brewtour.application.command.location.AddBeerRatingCommand;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.UserId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerStarRatingAddedByAnonymousEvent extends AbstractBeerStarRatingAddedEvent {

	@JsonCreator
	public BeerStarRatingAddedByAnonymousEvent(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("userId") UserId userId,
			@JsonProperty("name") String name,
			@JsonProperty("stars") int stars) {
		super(locationId, userId, name, stars);
	}

	public static BeerStarRatingAddedByAnonymousEvent fromCommand(AddBeerRatingCommand command, UserId userId) {
		return new BeerStarRatingAddedByAnonymousEvent(command.locationId, userId, command.name, command.stars);
	}

}
