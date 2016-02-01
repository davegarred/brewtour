package org.garred.brewtour.application.command.location;

import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddBeerReviewCommand extends AbstractLocationCommand {

	public final String name;
	public final int stars;
	public final String review;

	@JsonCreator
	public AddBeerReviewCommand(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("name") String name,
			@JsonProperty("stars") int stars,
			@JsonProperty("review") String review) {
		super(locationId);
		this.name = name;
		this.stars = stars;
		this.review = review;
	}

}
