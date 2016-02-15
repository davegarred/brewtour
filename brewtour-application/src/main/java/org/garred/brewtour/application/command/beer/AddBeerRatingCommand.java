package org.garred.brewtour.application.command.beer;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.ReviewMedal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddBeerRatingCommand extends AbstractBeerCommand {

	public final ReviewMedal medal;

	@JsonCreator
	public AddBeerRatingCommand(@JsonProperty("beerId") BeerId beerId,
			@JsonProperty("medal") ReviewMedal medal) {
		super(beerId);
		this.medal = medal;
	}
}
