package org.garred.brewtour.application.event.beer;

import java.math.BigDecimal;

import org.garred.brewtour.application.command.beer.AddBeerCommand;
import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerAddedEvent extends AbstractBeerEvent {

	public final String beerName;
	public final String description;
	public final LocationId breweryId;
	public final String breweryName;
	public final String style;
	public final String category;
	public final BigDecimal abv,ibu;

	@JsonCreator
	public BeerAddedEvent(@JsonProperty("beerId") BeerId beerId,
			@JsonProperty("beerName") String beerName,
			@JsonProperty("description") String description,
			@JsonProperty("breweryId") LocationId breweryId,
			@JsonProperty("breweryName") String breweryName,
			@JsonProperty("style") String style,
			@JsonProperty("category") String category,
			@JsonProperty("abv") BigDecimal abv,
			@JsonProperty("ibu") BigDecimal ibu) {
		super(beerId);
		this.beerName = beerName;
		this.description = description;
		this.breweryId = breweryId;
		this.breweryName = breweryName;
		this.style = style;
		this.category = category;
		this.abv = abv;
		this.ibu = ibu;
	}

	public static BeerAddedEvent fromCommand(AddBeerCommand command, BeerId beerId) {
		return new BeerAddedEvent(beerId, command.beerName, command.description, command.breweryId, command.breweryName, command.style, command.category, command.abv, command.ibu);
	}

}
