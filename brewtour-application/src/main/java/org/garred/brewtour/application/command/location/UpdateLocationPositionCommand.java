package org.garred.brewtour.application.command.location;

import java.math.BigDecimal;

import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
