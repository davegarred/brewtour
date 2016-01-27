package org.garred.brewtour.application.event;

import org.garred.brewtour.api.BeerUnavailableCommand;
import org.garred.brewtour.application.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerUnavailableEvent extends AbstractLocationEvent {

	public final String name;

	@JsonCreator
	public BeerUnavailableEvent(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("name") String name) {
		super(locationId);
		this.name = name;
	}

	public static BeerUnavailableEvent fromCommand(BeerUnavailableCommand command) {
		return new BeerUnavailableEvent(command.locationId, command.name);
	}

}
