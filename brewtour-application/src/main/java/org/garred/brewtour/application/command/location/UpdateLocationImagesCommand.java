package org.garred.brewtour.application.command.location;

import static org.garred.brewtour.view.UserAuthView.ADMIN_ROLE;

import org.garred.brewtour.domain.AvailableImages;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.security.SecuredCommand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@SecuredCommand(ADMIN_ROLE)
public class UpdateLocationImagesCommand extends AbstractLocationCommand {

    public final AvailableImages images;

	@JsonCreator
	public UpdateLocationImagesCommand(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("images") AvailableImages images) {
		super(locationId);
        this.images = images;
	}

}
