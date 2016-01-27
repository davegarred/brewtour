package org.garred.brewtour.application.event;

import org.garred.brewtour.api.BeerAvailableCommand;
import org.garred.brewtour.application.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerAvailableEvent extends AbstractLocationEvent {

	public final String name;

	@JsonCreator
	public BeerAvailableEvent(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("name") String name) {
		super(locationId);
		this.name = name;
	}

	public static BeerAvailableEvent fromCommand(BeerAvailableCommand command) {
		return new BeerAvailableEvent(command.locationId, command.name);
	}

}
