package org.garred.brewtour.api;

import org.garred.brewtour.application.LocationId;
import org.garred.brewtour.application.Review;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddLocationReviewCommand extends AbstractLocationCommand {

	public final Review review;

	@JsonCreator
	public AddLocationReviewCommand(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("review") Review review) {
		super(locationId);
		this.review = review;
	}

}
