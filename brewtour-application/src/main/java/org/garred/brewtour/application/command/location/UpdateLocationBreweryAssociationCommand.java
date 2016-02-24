package org.garred.brewtour.application.command.location;

import static org.garred.brewtour.view.UserAuthView.ADMIN_ROLE;

import org.garred.brewtour.domain.BreweryId;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.security.SecuredCommand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@SecuredCommand(ADMIN_ROLE)
public class UpdateLocationBreweryAssociationCommand extends AbstractLocationCommand {

    public final BreweryId breweryId;

	@JsonCreator
	public UpdateLocationBreweryAssociationCommand(
			@JsonProperty("locationId") LocationId locationId,
	        @JsonProperty("breweryId") BreweryId breweryId
	        ) {
		super(locationId);
        this.breweryId = breweryId;
	}

}
