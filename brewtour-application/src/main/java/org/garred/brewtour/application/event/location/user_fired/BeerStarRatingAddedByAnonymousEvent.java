package org.garred.brewtour.application.event.location.user_fired;

import static org.garred.brewtour.security.GuestUserAuth.ANONYMOUS_SCREEN_NAME;

import org.garred.brewtour.application.command.location.AddBeerRatingCommand;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.ReviewMedal;
import org.garred.brewtour.domain.UserId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerStarRatingAddedByAnonymousEvent extends AbstractBeerStarRatingAddedEvent {

	@JsonCreator
	public BeerStarRatingAddedByAnonymousEvent(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("userId") UserId userId,
			@JsonProperty("beerName") String beerName,
			@JsonProperty("medal") ReviewMedal medal) {
		super(locationId, userId, ANONYMOUS_SCREEN_NAME, beerName, medal);
	}

	public static BeerStarRatingAddedByAnonymousEvent fromCommand(AddBeerRatingCommand command, UserId userId) {
		return new BeerStarRatingAddedByAnonymousEvent(command.locationId, userId, command.beerName, command.medal);
	}

}
