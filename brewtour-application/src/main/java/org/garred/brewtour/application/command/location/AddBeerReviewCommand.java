package org.garred.brewtour.application.command.location;

import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.ReviewMedal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddBeerReviewCommand extends AbstractLocationBeerCommand {

	public final ReviewMedal medal;
	public final String review;

	@JsonCreator
	public AddBeerReviewCommand(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("beerName") String beerName,
			@JsonProperty("medal") ReviewMedal medal,
			@JsonProperty("review") String review) {
		super(locationId, beerName);
		this.medal = medal;
		this.review = review;
	}

}
