package org.garred.brewtour.domain.event.beer;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.command.beer.AddBeerCommand;

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
