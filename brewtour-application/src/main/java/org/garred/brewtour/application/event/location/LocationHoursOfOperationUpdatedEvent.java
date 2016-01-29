package org.garred.brewtour.application.event.location;

import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationHoursOfOperationUpdatedEvent extends AbstractLocationEvent {

    public final String hoursOfOperation;

	@JsonCreator
	public LocationHoursOfOperationUpdatedEvent(@JsonProperty("locationId") LocationId locationId,
	        @JsonProperty("hoursOfOperation") String hoursOfOperation) {
		super(locationId);
		this.hoursOfOperation = hoursOfOperation;
	}


}
