package org.garred.brewtour.application.event.location;

import org.garred.brewtour.application.command.location.AddBeerReviewCommand;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.Review;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerReviewAddedEvent extends AbstractLocationEvent {

	public final String name;
	public final Review review;

	@JsonCreator
	public BeerReviewAddedEvent(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("name") String name,
			@JsonProperty("review") Review review) {
		super(locationId);
		this.name = name;
		this.review = review;
	}

	public static BeerReviewAddedEvent fromCommand(AddBeerReviewCommand command) {
		return new BeerReviewAddedEvent(command.locationId, command.name, command.review);
	}

}