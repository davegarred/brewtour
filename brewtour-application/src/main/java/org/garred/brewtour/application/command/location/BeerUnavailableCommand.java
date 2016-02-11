package org.garred.brewtour.application.command.location;

import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerUnavailableCommand extends AbstractLocationBeerCommand {

	@JsonCreator
	public BeerUnavailableCommand(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("beerName") String beerName) {
		super(locationId, beerName);
	}

}
