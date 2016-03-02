package org.garred.brewtour.domain.event.beer;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.ReviewMedal;
import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.domain.command.beer.AddBeerRatingCommand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerStarRatingAddedByUserEvent extends AbstractBeerStarRatingAddedEvent {

	@JsonCreator
	public BeerStarRatingAddedByUserEvent(@JsonProperty("beerId") BeerId beerId,
			@JsonProperty("userId") UserId userId,
			@JsonProperty("userScreenName") String userScreenName,
			@JsonProperty("medal") ReviewMedal medal) {
		super(beerId, userId, userScreenName, medal);
	}

	public static BeerStarRatingAddedByUserEvent fromCommand(AddBeerRatingCommand command, UserId userId, String userScreenName) {
		return new BeerStarRatingAddedByUserEvent(command.beerId, userId, userScreenName, command.medal);
	}

}
