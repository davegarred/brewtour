package org.garred.brewtour.application.command.location;

import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateLocationPhoneCommand extends AbstractLocationCommand {

    public final String phoneNumber;

	@JsonCreator
	public UpdateLocationPhoneCommand(
			@JsonProperty("locationId") LocationId locationId,
	        @JsonProperty("phoneNumber") String phoneNumber
	        ) {
		super(locationId);
        this.phoneNumber = phoneNumber;
	}

}
