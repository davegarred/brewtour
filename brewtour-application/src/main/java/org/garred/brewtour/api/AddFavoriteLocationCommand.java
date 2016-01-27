package org.garred.brewtour.api;

import org.garred.brewtour.application.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddFavoriteLocationCommand extends AbstractUserFiredCommand {

	public final LocationId locationId;

	@JsonCreator
	public AddFavoriteLocationCommand(@JsonProperty("locationId") LocationId locationId) {
		this.locationId = locationId;
	}

}
