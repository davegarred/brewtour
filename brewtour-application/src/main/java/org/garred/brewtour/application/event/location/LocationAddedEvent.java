package org.garred.brewtour.application.event.location;

import org.garred.brewtour.application.command.location.AddLocationCommand;
import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationAddedEvent extends AbstractLocationAddedEvent {

	@JsonCreator
	public LocationAddedEvent(@JsonProperty("locationId") LocationId locationId,
	        @JsonProperty("name") String name) {
		super(locationId, name);
	}

	public static LocationAddedEvent fromCommand(LocationId locationId, AddLocationCommand command) {
		return new LocationAddedEvent(locationId, command.name);
	}

}
