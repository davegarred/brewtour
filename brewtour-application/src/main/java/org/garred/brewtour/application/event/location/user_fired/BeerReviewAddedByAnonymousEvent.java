package org.garred.brewtour.application.event.location.user_fired;

import static org.garred.brewtour.security.GuestUserAuth.ANONYMOUS_SCREEN_NAME;

import java.time.LocalDateTime;

import org.garred.brewtour.application.command.location.AddBeerReviewCommand;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.ReviewMedal;
import org.garred.brewtour.domain.UserId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerReviewAddedByAnonymousEvent extends AbstractBeerReviewAddedEvent {

	@JsonCreator
	public BeerReviewAddedByAnonymousEvent(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("userId") UserId userId,
			@JsonProperty("beerName") String beerName,
			@JsonProperty("medal") ReviewMedal medal,
			@JsonProperty("time") LocalDateTime time,
			@JsonProperty("review") String review) {
		super(locationId, userId, ANONYMOUS_SCREEN_NAME, beerName, medal, time, review);
	}

	public static BeerReviewAddedByAnonymousEvent fromCommand(AddBeerReviewCommand command, UserId userId, LocalDateTime time) {
		return new BeerReviewAddedByAnonymousEvent(command.locationId, userId, command.beerName, command.medal, time, command.review);
	}

}
