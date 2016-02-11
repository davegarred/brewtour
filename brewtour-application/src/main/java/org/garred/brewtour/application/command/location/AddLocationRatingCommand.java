package org.garred.brewtour.application.command.location;

import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.ReviewMedal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddLocationRatingCommand extends AbstractLocationCommand {

	public final ReviewMedal medal;

	@JsonCreator
	public AddLocationRatingCommand(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("medal") ReviewMedal medal) {
		super(locationId);
		this.medal = medal;
	}

}
