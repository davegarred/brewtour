package org.garred.brewtour.application.command.location;

import static org.garred.brewtour.view.UserAuthView.ADMIN_ROLE;

import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.security.SecuredCommand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@SecuredCommand(ADMIN_ROLE)
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
