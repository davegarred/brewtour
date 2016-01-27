package org.garred.brewtour.application.event;

import java.math.BigDecimal;

import org.garred.brewtour.api.AddLocationCommand;
import org.garred.brewtour.application.AvailableImages;
import org.garred.brewtour.application.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationAddedEvent extends AbstractLocationAddedEvent {

	@JsonCreator
	public LocationAddedEvent(@JsonProperty("locationId") LocationId locationId,
	        @JsonProperty("brewDbId") String brewDbId,
	        @JsonProperty("name") String name,
	        @JsonProperty("description") String description,
	        @JsonProperty("latitude") BigDecimal latitude,
	        @JsonProperty("longitude") BigDecimal longitude,
	        @JsonProperty("images") AvailableImages images) {
		super(locationId, brewDbId, name, description, latitude, longitude, images);
	}

	public static LocationAddedEvent fromCommand(LocationId locationId, AddLocationCommand command) {
		return new LocationAddedEvent(locationId, command.brewDbId, command.name,
				command.description, command.latitude, command.longitude, command.images);
	}

}
