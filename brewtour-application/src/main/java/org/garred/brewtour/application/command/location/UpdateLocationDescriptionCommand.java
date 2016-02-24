package org.garred.brewtour.application.command.location;

import static org.garred.brewtour.view.UserAuthView.ADMIN_ROLE;

import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.security.SecuredCommand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@SecuredCommand(ADMIN_ROLE)
public class UpdateLocationDescriptionCommand extends AbstractLocationCommand {

	public final String description;

	@JsonCreator
	public UpdateLocationDescriptionCommand(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("description") String description) {
		super(locationId);
		this.description = description;
	}

}
