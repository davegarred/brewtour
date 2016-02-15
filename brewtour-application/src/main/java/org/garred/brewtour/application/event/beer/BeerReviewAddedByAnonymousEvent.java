package org.garred.brewtour.application.event.beer;

import static org.garred.brewtour.security.GuestUserAuth.ANONYMOUS_SCREEN_NAME;

import java.time.LocalDateTime;

import org.garred.brewtour.application.command.beer.AddBeerReviewCommand;
import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.ReviewMedal;
import org.garred.brewtour.domain.UserId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerReviewAddedByAnonymousEvent extends AbstractBeerReviewAddedEvent {

	@JsonCreator
	public BeerReviewAddedByAnonymousEvent(@JsonProperty("beerId") BeerId beerId,
			@JsonProperty("userId") UserId userId,
			@JsonProperty("userScreenName") String userScreenName,
			@JsonProperty("medal") ReviewMedal medal,
			@JsonProperty("time") LocalDateTime time,
			@JsonProperty("review") String review) {
		super(beerId, userId, userScreenName, medal, time, review);
	}

	public static BeerReviewAddedByAnonymousEvent fromCommand(AddBeerReviewCommand command, UserId userId, LocalDateTime time) {
		return new BeerReviewAddedByAnonymousEvent(command.beerId, userId, ANONYMOUS_SCREEN_NAME, command.medal, time, command.review);
	}

}
