package org.garred.brewtour.application.event.location;

import java.time.LocalDateTime;

import org.garred.brewtour.application.command.location.AddBeerReviewCommand;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.Review;
import org.garred.brewtour.domain.UserId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerReviewAddedByUserEvent extends AbstractBeerReviewAddedEvent {

	@JsonCreator
	public BeerReviewAddedByUserEvent(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("name") String name,
			@JsonProperty("review") Review review) {
		super(locationId, name, review);
	}

	public static BeerReviewAddedByUserEvent fromCommand(AddBeerReviewCommand command, UserId userId, LocalDateTime time) {
		final Review review = new Review(userId, time, command.stars, command.review);
		return new BeerReviewAddedByUserEvent(command.locationId, command.name, review);
	}

}
