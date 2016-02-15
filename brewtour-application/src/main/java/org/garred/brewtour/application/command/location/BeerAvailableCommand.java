package org.garred.brewtour.application.command.location;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerAvailableCommand extends AbstractLocationCommand {

	public final BeerId beerId;
	@JsonCreator
	public BeerAvailableCommand(
			@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("beerId") BeerId beerId
			) {
		super(locationId);
		this.beerId = beerId;
	}

}
