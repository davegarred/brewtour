package org.garred.brewtour.application.event.location;

import org.garred.brewtour.application.command.location.BeerUnavailableCommand;
import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerUnavailableEvent extends AbstractLocationBeerEvent {

	@JsonCreator
	public BeerUnavailableEvent(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("beerName") String beerName) {
		super(locationId, beerName);
	}

	public static BeerUnavailableEvent fromCommand(BeerUnavailableCommand command) {
		return new BeerUnavailableEvent(command.locationId, command.beerName);
	}

}
