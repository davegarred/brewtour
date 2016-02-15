package org.garred.brewtour.application.event.beer;

import java.math.BigDecimal;

import org.garred.brewtour.application.command.beer.AbstractAddBeerCommand;
import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.BreweryId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerAddedEvent extends AbstractBeerAddedEvent {

	@JsonCreator
	public BeerAddedEvent(@JsonProperty("beerId") BeerId beerId,
			@JsonProperty("beerName") String beerName,
			@JsonProperty("breweryId") BreweryId breweryId,
			@JsonProperty("breweryName") String breweryName,
			@JsonProperty("style") String style,
			@JsonProperty("category") String category,
			@JsonProperty("abv") BigDecimal abv,
			@JsonProperty("ibu") BigDecimal ibu) {
		super(beerId, beerName, breweryId, breweryName, style, category, abv, ibu);
	}

	public static BeerAddedEvent fromCommand(AbstractAddBeerCommand command, BeerId beerId) {
		return new BeerAddedEvent(beerId, command.beerName, command.breweryId, command.breweryName, command.style, command.category, command.abv, command.ibu);
	}

}
