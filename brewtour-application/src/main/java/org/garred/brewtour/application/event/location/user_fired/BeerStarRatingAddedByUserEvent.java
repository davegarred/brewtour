package org.garred.brewtour.application.event.location.user_fired;

import org.garred.brewtour.application.command.location.AddBeerRatingCommand;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.ReviewMedal;
import org.garred.brewtour.domain.UserId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerStarRatingAddedByUserEvent extends AbstractBeerStarRatingAddedEvent {

	@JsonCreator
	public BeerStarRatingAddedByUserEvent(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("userId") UserId userId,
			@JsonProperty("userScreenName") String userScreenName,
			@JsonProperty("beerName") String beerName,
			@JsonProperty("medal") ReviewMedal medal) {
		super(locationId, userId, userScreenName, beerName, medal);
	}

	public static BeerStarRatingAddedByUserEvent fromCommand(AddBeerRatingCommand command, UserId userId, String userScreenName) {
		return new BeerStarRatingAddedByUserEvent(command.locationId, userId, userScreenName, command.beerName, command.medal);
	}

}
