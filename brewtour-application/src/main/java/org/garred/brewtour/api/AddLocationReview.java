package org.garred.brewtour.api;

import org.garred.brewtour.application.LocationId;
import org.garred.brewtour.application.Review;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddLocationReview extends LocationCommand {

	public final Review review;

	@JsonCreator
	public AddLocationReview(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("review") Review review) {
		super(locationId);
		this.review = review;
	}

}
