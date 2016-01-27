package org.garred.brewtour.api;

import org.garred.brewtour.application.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ModifyLocationDescriptionCommand extends AbstractLocationCommand {

	public final String description;

	@JsonCreator
	public ModifyLocationDescriptionCommand(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("description") String description) {
		super(locationId);
		this.description = description;
	}

}
