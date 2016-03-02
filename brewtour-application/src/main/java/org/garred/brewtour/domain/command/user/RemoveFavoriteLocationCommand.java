package org.garred.brewtour.domain.command.user;

import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RemoveFavoriteLocationCommand extends AbstractUserFiredCommand {

	public final LocationId locationId;

	@JsonCreator
	public RemoveFavoriteLocationCommand(@JsonProperty("locationId") LocationId locationId) {
		this.locationId = locationId;
	}

}
