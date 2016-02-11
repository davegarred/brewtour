package org.garred.brewtour.application.command.location;

import java.math.BigDecimal;

import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddBeerCommand extends AbstractLocationBeerCommand {

	public final String style,category;
	public final BigDecimal abv,ibu;

	@JsonCreator
	public AddBeerCommand(@JsonProperty("locationId") LocationId locationId,
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

}
