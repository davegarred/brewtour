package org.garred.brewtour.application.command.location;

import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.ReviewMedal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddBeerRatingCommand extends AbstractLocationBeerCommand {

	public final ReviewMedal medal;

	@JsonCreator
	public AddBeerRatingCommand(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("beerName") String beerName,
			@JsonProperty("medal") ReviewMedal medal) {
		super(locationId, beerName);
		this.medal = medal;
	}

}
