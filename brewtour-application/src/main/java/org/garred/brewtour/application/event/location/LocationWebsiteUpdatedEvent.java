package org.garred.brewtour.application.event.location;

import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationWebsiteUpdatedEvent extends AbstractLocationEvent {

    public final String website;

	@JsonCreator
	public LocationWebsiteUpdatedEvent(@JsonProperty("locationId") LocationId locationId,
	        @JsonProperty("website") String website) {
		super(locationId);
		this.website = website;
	}


}
