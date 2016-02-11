package org.garred.brewtour.application.event.location;

import java.math.BigDecimal;

import org.garred.brewtour.application.command.location.ModifyBeerCommand;
import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerModifiedEvent extends AbstractLocationBeerEvent {

	public final String style,category;
	public final BigDecimal abv,ibu;

	@JsonCreator
	public BeerModifiedEvent(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("beerName") String beerName,
			@JsonProperty("style") String style,
			@JsonProperty("category") String category,
			@JsonProperty("abv") BigDecimal abv,
			@JsonProperty("ibu") BigDecimal ibu) {
		super(locationId, beerName);
		this.style = style;
		this.category = category;
		this.abv = abv;
		this.ibu = ibu;
	}

	public static BeerModifiedEvent fromCommand(ModifyBeerCommand command) {
		return new BeerModifiedEvent(command.locationId, command.beerName, command.style, command.category, command.abv, command.ibu);
	}
}
