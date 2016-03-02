package org.garred.brewtour.domain.event.location;

import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationBreweryAssociationUpdatedEvent extends AbstractLocationEvent {

    public final LocationId associatedBreweryId;

	@JsonCreator
	public LocationBreweryAssociationUpdatedEvent(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("associatedBreweryId") LocationId associatedBreweryId) {
		super(locationId);
		this.associatedBreweryId = associatedBreweryId;
	}


}
