package org.garred.brewtour.application.command.location;

import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.ReviewMedal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddLocationReviewCommand extends AbstractLocationCommand {

	public final ReviewMedal medal;
	public final String review;


	@JsonCreator
	public AddLocationReviewCommand(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("medal") ReviewMedal medal,
			@JsonProperty("review") String review) {
		super(locationId);
		this.medal = medal;
		this.review = review;
	}

}
