package org.garred.brewtour.api;

import org.garred.brewtour.application.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerUnavailableCommand extends AbstractLocationCommand {

	public final String name;

	@JsonCreator
	public BeerUnavailableCommand(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("name") String name) {
		super(locationId);
		this.name = name;
	}

}
