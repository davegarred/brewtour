package org.garred.brewtour.application.event.location;

import java.math.BigDecimal;

import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationPositionUpdatedEvent extends AbstractLocationEvent {

    public final BigDecimal latitude;
    public final BigDecimal longitude;

	@JsonCreator
	public LocationPositionUpdatedEvent(@JsonProperty("locationId") LocationId locationId,
	        @JsonProperty("latitude") BigDecimal latitude,
	        @JsonProperty("longitude") BigDecimal longitude) {
		super(locationId);
		this.latitude = latitude;
		this.longitude = longitude;
	}


}
