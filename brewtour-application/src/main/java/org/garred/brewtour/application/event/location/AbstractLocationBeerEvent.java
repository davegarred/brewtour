package org.garred.brewtour.application.event.location;

import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;

public abstract class AbstractLocationBeerEvent extends AbstractLocationEvent {

	public final String beerName;

	@JsonCreator
	public AbstractLocationBeerEvent(LocationId locationId, String beerName) {
		super(locationId);
		this.beerName = beerName;
	}

}
