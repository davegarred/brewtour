package org.garred.brewtour.application.command.location;

import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.ReviewMedal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddBeerRatingCommand extends AbstractLocationCommand {

	public final String name;
	public final ReviewMedal medal;

	@JsonCreator
	public AddBeerRatingCommand(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("name") String name,
			@JsonProperty("medal") ReviewMedal medal) {
		super(locationId);
		this.name = name;
		this.medal = medal;
	}

}
