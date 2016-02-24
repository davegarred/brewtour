package org.garred.brewtour.application.command.location;

import static org.garred.brewtour.view.UserAuthView.ADMIN_ROLE;

import java.math.BigDecimal;

import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.security.SecuredCommand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@SecuredCommand(ADMIN_ROLE)
public class UpdateLocationPositionCommand extends AbstractLocationCommand {

    public final BigDecimal latitude;
    public final BigDecimal longitude;

	@JsonCreator
	public UpdateLocationPositionCommand(
			@JsonProperty("locationId") LocationId locationId,
	        @JsonProperty("latitude") BigDecimal latitude,
	        @JsonProperty("longitude") BigDecimal longitude
	        ) {
		super(locationId);
        this.latitude = latitude;
        this.longitude = longitude;
	}

}
