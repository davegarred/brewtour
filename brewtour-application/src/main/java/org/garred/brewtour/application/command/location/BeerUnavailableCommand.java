package org.garred.brewtour.application.command.location;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerUnavailableCommand extends AbstractLocationCommand {

	public final BeerId beerId;
	@JsonCreator
	public BeerUnavailableCommand(
			@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("beerName") BeerId beerId
			) {
		super(locationId);
		this.beerId = beerId;
	}


}
