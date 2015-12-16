package org.garred.brewtour.api;

import org.garred.brewtour.application.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddFavoriteLocation extends LocationCommand {

	@JsonCreator
	public AddFavoriteLocation(@JsonProperty("locationId") LocationId locationId) {
		super(locationId);
	}

}
