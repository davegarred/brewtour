package org.garred.brewtour.application.command.location;

import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateLocationHoursOfOperationCommand extends AbstractLocationCommand {

    public final String hoursOfOperation;

	@JsonCreator
	public UpdateLocationHoursOfOperationCommand(
			@JsonProperty("locationId") LocationId locationId,
	        @JsonProperty("hoursOfOperation") String hoursOfOperation
	        ) {
		super(locationId);
        this.hoursOfOperation = hoursOfOperation;
	}

}
