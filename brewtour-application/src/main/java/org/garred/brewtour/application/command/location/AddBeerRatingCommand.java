package org.garred.brewtour.application.command.location;

import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddBeerRatingCommand extends AbstractLocationCommand {

	public final String name;
	public final int stars;

	@JsonCreator
	public AddBeerRatingCommand(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("name") String name,
			@JsonProperty("stars") int stars) {
		super(locationId);
		this.name = name;
		this.stars = stars;
	}

}
