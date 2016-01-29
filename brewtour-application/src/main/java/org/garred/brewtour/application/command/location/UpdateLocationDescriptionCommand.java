package org.garred.brewtour.application.command.location;

import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateLocationDescriptionCommand extends AbstractLocationCommand {

	public final String description;

	@JsonCreator
	public UpdateLocationDescriptionCommand(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("description") String description) {
		super(locationId);
		this.description = description;
	}

}
