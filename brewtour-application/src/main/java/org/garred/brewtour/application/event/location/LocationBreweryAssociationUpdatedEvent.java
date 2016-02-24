package org.garred.brewtour.application.event.location;

import org.garred.brewtour.domain.BreweryId;
import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationBreweryAssociationUpdatedEvent extends AbstractLocationEvent {

    public final BreweryId breweryId;

	@JsonCreator
	public LocationBreweryAssociationUpdatedEvent(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("breweryId") BreweryId breweryId) {
		super(locationId);
		this.breweryId = breweryId;
	}


}
