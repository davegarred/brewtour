package org.garred.brewtour.application.command.beer;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.ReviewMedal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddBeerReviewCommand extends AbstractBeerCommand {

	public final ReviewMedal medal;
	public final String review;

	@JsonCreator
	public AddBeerReviewCommand(@JsonProperty("beerId") BeerId beerId,
			@JsonProperty("medal") ReviewMedal medal,
			@JsonProperty("review") String review) {
		super(beerId);
		this.medal = medal;
		this.review = review;
	}

}
