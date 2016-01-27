package org.garred.brewtour.application.event;

import org.garred.brewtour.api.ModifyLocationDescriptionCommand;
import org.garred.brewtour.application.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationDescriptionModifiedEvent extends AbstractLocationEvent {

	public final String description;

	@JsonCreator
	public LocationDescriptionModifiedEvent(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("description") String description) {
		super(locationId);
		this.description = description;
	}

	public static LocationDescriptionModifiedEvent fromCommand(ModifyLocationDescriptionCommand command) {
		return new LocationDescriptionModifiedEvent(command.locationId, command.description);
	}

}
