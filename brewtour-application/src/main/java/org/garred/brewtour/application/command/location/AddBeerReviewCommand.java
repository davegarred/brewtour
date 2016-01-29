package org.garred.brewtour.application.command.location;

import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.Review;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddBeerReviewCommand extends AbstractLocationCommand {

	public final String name;
	public final Review review;

	@JsonCreator
	public AddBeerReviewCommand(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("name") String name,
			@JsonProperty("review") Review review) {
		super(locationId);
		this.name = name;
		this.review = review;
	}

}
