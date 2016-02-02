package org.garred.brewtour.application.event.location.user_fired;

import java.time.LocalDateTime;

import org.garred.brewtour.application.command.location.AddBeerReviewCommand;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.UserId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerReviewAddedByAnonymousEvent extends AbstractBeerReviewAddedEvent {

	@JsonCreator
	public BeerReviewAddedByAnonymousEvent(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("userId") UserId userId,
			@JsonProperty("name") String name,
			@JsonProperty("stars") int stars,
			@JsonProperty("time") LocalDateTime time,
			@JsonProperty("review") String review) {
		super(locationId, userId, name, stars, time, review);
	}

	public static BeerReviewAddedByAnonymousEvent fromCommand(AddBeerReviewCommand command, UserId userId, LocalDateTime time) {
		return new BeerReviewAddedByAnonymousEvent(command.locationId, userId, command.name, command.stars, time, command.review);
	}

}
