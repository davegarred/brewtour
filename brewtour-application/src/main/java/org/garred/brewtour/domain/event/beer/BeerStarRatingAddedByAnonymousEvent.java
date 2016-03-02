package org.garred.brewtour.domain.event.beer;

import static org.garred.brewtour.security.GuestUserAuth.ANONYMOUS_SCREEN_NAME;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.ReviewMedal;
import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.domain.command.beer.AddBeerRatingCommand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerStarRatingAddedByAnonymousEvent extends AbstractBeerStarRatingAddedEvent {

	@JsonCreator
	public BeerStarRatingAddedByAnonymousEvent(@JsonProperty("beerId") BeerId beerId,
			@JsonProperty("userId") UserId userId,
			@JsonProperty("userScreenName") String userScreenName,
			@JsonProperty("medal") ReviewMedal medal) {
		super(beerId, userId, userScreenName, medal);
	}

	public static BeerStarRatingAddedByAnonymousEvent fromCommand(AddBeerRatingCommand command, UserId userId) {
		return new BeerStarRatingAddedByAnonymousEvent(command.beerId, userId, ANONYMOUS_SCREEN_NAME, command.medal);
	}

}
