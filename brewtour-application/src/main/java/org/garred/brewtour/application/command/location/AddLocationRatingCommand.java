package org.garred.brewtour.application.command.location;

import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddLocationRatingCommand extends AbstractLocationCommand {

	public final int stars;

	@JsonCreator
	public AddLocationRatingCommand(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("stars") int stars) {
		super(locationId);
		this.stars = stars;
	}

}
