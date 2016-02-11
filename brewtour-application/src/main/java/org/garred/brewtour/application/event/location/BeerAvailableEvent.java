package org.garred.brewtour.application.event.location;

import org.garred.brewtour.application.command.location.BeerAvailableCommand;
import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerAvailableEvent extends AbstractLocationBeerEvent {

	@JsonCreator
	public BeerAvailableEvent(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("beerName") String beerName) {
		super(locationId, beerName);
	}

	public static BeerAvailableEvent fromCommand(BeerAvailableCommand command) {
		return new BeerAvailableEvent(command.locationId, command.beerName);
	}

}
