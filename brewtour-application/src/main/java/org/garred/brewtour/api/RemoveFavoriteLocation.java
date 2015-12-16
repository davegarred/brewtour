package org.garred.brewtour.api;

import org.garred.brewtour.application.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RemoveFavoriteLocation extends LocationCommand {

	@JsonCreator
	public RemoveFavoriteLocation(@JsonProperty("locationId") LocationId locationId) {
		super(locationId);
	}

}
