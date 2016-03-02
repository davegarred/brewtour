package org.garred.brewtour.domain.event.location;

import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.command.location.UpdateLocationDescriptionCommand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationDescriptionUpdatedEvent extends AbstractLocationEvent {

	public final String description;

	@JsonCreator
	public LocationDescriptionUpdatedEvent(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("description") String description) {
		super(locationId);
		this.description = description;
	}

	public static LocationDescriptionUpdatedEvent fromCommand(UpdateLocationDescriptionCommand command) {
		return new LocationDescriptionUpdatedEvent(command.locationId, command.description);
	}

}
