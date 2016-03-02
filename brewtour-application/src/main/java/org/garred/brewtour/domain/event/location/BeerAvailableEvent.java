package org.garred.brewtour.domain.event.location;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerAvailableEvent extends AbstractLocationEvent {

	public final BeerId beerId;

	@JsonCreator
	public BeerAvailableEvent(
			@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("beerId") BeerId beerId
			) {
		super(locationId);
		this.beerId = beerId;
	}

}
