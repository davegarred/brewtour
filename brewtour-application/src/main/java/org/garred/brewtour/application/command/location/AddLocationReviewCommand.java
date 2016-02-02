package org.garred.brewtour.application.command.location;

import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddLocationReviewCommand extends AbstractLocationCommand {

	public final int stars;
	public final String review;


	@JsonCreator
	public AddLocationReviewCommand(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("stars") int stars,
			@JsonProperty("review") String review) {
		super(locationId);
		this.stars = stars;
		this.review = review;
	}

}
