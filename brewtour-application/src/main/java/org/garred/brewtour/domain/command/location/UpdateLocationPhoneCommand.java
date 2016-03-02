package org.garred.brewtour.domain.command.location;

import static org.garred.brewtour.view.UserAuthView.ADMIN_ROLE;

import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.security.SecuredCommand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@SecuredCommand(ADMIN_ROLE)
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
