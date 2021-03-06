package org.garred.brewtour.domain.command.location;

import static org.garred.brewtour.view.UserAuthView.ADMIN_ROLE;

import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.security.SecuredCommand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@SecuredCommand(ADMIN_ROLE)
public class UpdateLocationWebsiteCommand extends AbstractLocationCommand {

    public final String website;

	@JsonCreator
	public UpdateLocationWebsiteCommand(
			@JsonProperty("locationId") LocationId locationId,
	        @JsonProperty("website") String website
	        ) {
		super(locationId);
        this.website = website;
	}

}
