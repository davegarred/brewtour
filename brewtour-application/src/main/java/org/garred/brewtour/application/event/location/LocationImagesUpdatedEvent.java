package org.garred.brewtour.application.event.location;

import org.garred.brewtour.domain.AvailableImages;
import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationImagesUpdatedEvent extends AbstractLocationEvent {

    public final AvailableImages images;

	@JsonCreator
	public LocationImagesUpdatedEvent(@JsonProperty("locationId") LocationId locationId,
	        @JsonProperty("images") AvailableImages images) {
		super(locationId);
		this.images = images;
	}


}
