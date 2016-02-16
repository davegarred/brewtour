package org.garred.brewtour.application.event.beer;

import java.time.LocalDateTime;

import org.garred.brewtour.application.command.beer.AddBeerReviewCommand;
import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.ReviewMedal;
import org.garred.brewtour.domain.UserId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerReviewAddedByUserEvent extends AbstractBeerReviewAddedEvent {

	@JsonCreator
	public BeerReviewAddedByUserEvent(@JsonProperty("beerId") BeerId beerId,
			@JsonProperty("userId") UserId userId,
			@JsonProperty("userScreenName") String userScreenName,
			@JsonProperty("medal") ReviewMedal medal,
			@JsonProperty("time") LocalDateTime time,
			@JsonProperty("review") String review) {
		super(beerId, userId, userScreenName, medal, time, review);
	}

	public static BeerReviewAddedByUserEvent fromCommand(AddBeerReviewCommand command, UserId userId, String userScreenName, LocalDateTime time) {
		return new BeerReviewAddedByUserEvent(command.beerId, userId, userScreenName, command.medal, time, command.review);
	}

}
