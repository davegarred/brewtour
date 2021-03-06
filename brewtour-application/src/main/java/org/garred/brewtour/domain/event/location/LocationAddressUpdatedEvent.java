package org.garred.brewtour.domain.event.location;

import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.command.location.UpdateLocationAddressCommand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationAddressUpdatedEvent extends AbstractLocationEvent {

    public final String streetAddress;
    public final String streetAddress2;
    public final String city;
    public final String state;
    public final String postalCode;

	@JsonCreator
	public LocationAddressUpdatedEvent(@JsonProperty("locationId") LocationId locationId,
	        @JsonProperty("streetAddress") String streetAddress,
	        @JsonProperty("streetAddress2") String streetAddress2,
	        @JsonProperty("city") String city,
	        @JsonProperty("state") String state,
	        @JsonProperty("postalCode") String postalCode) {
		super(locationId);
		this.streetAddress = streetAddress;
		this.streetAddress2 = streetAddress2;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
	}

	public static Object fromCommand(UpdateLocationAddressCommand command) {
		return new LocationAddressUpdatedEvent(command.locationId, command.streetAddress, command.streetAddress2, command.city, command.state, command.postalCode);
	}

}
