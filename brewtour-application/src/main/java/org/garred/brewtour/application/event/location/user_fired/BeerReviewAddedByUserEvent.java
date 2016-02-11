package org.garred.brewtour.application.event.location.user_fired;

import java.time.LocalDateTime;

import org.garred.brewtour.application.command.location.AddBeerReviewCommand;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.ReviewMedal;
import org.garred.brewtour.domain.UserId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerReviewAddedByUserEvent extends AbstractBeerReviewAddedEvent {

	@JsonCreator
	public BeerReviewAddedByUserEvent(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("userId") UserId userId,
			@JsonProperty("userScreenName") String userScreenName,
			@JsonProperty("beerName") String beerName,
			@JsonProperty("medal") ReviewMedal medal,
			@JsonProperty("time") LocalDateTime time,
			@JsonProperty("review") String review) {
		super(locationId, userId, userScreenName, beerName, medal, time, review);
	}

	public static BeerReviewAddedByUserEvent fromCommand(AddBeerReviewCommand command, UserId userId, String userScreenName, LocalDateTime time) {
		return new BeerReviewAddedByUserEvent(command.locationId, userId, userScreenName, command.beerName, command.medal, time, command.review);
	}

}
