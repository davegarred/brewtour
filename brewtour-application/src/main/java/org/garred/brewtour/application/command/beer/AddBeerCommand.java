package org.garred.brewtour.application.command.beer;

import java.math.BigDecimal;

import org.garred.brewtour.domain.BreweryId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddBeerCommand extends AbstractAddBeerCommand {

	@JsonCreator
	public AddBeerCommand(
			@JsonProperty("beerName") String beerName,
			@JsonProperty("breweryId") BreweryId breweryId,
			@JsonProperty("breweryName") String breweryName,
			@JsonProperty("style") String style,
			@JsonProperty("category") String category,
			@JsonProperty("abv") BigDecimal abv,
			@JsonProperty("ibu") BigDecimal ibu) {
		super(beerName, breweryId, breweryName, style, category, abv, ibu);
	}

}
