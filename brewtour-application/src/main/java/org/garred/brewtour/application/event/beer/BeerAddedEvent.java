package org.garred.brewtour.application.event.beer;

import org.garred.brewtour.application.command.beer.AddBeerCommand;
import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerAddedEvent extends AbstractBeerEvent {

	public final String beerName;
	public final LocationId breweryId;
	public final String breweryName;

	@JsonCreator
	public BeerAddedEvent(@JsonProperty("beerId") BeerId beerId,
			@JsonProperty("beerName") String beerName,
			@JsonProperty("breweryId") LocationId breweryId,
			@JsonProperty("breweryName") String breweryName) {
		super(beerId);
		this.beerName = beerName;
		this.breweryId = breweryId;
		this.breweryName = breweryName;
	}

	public static BeerAddedEvent fromCommand(AddBeerCommand command, BeerId beerId) {
		return new BeerAddedEvent(beerId, command.beerName, command.breweryId, command.breweryName);
	}

}
