package org.garred.brewtour.domain.event.location;

import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationPhoneUpdatedEvent extends AbstractLocationEvent {

    public final String phoneNumber;

	@JsonCreator
	public LocationPhoneUpdatedEvent(@JsonProperty("locationId") LocationId locationId,
	        @JsonProperty("phoneNumber") String phoneNumber) {
		super(locationId);
        this.phoneNumber = phoneNumber;
	}

}
