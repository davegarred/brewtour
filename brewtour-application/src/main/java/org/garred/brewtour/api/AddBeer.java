package org.garred.brewtour.api;

import java.math.BigDecimal;

import org.garred.brewtour.application.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddBeer extends LocationCommand {

	public final String name,style,category;
	public final BigDecimal abv,ibu;

	@JsonCreator
	public AddBeer(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("name") String name,
			@JsonProperty("style") String style,
			@JsonProperty("category") String category,
			@JsonProperty("abv") BigDecimal abv,
			@JsonProperty("ibu") BigDecimal ibu) {
		super(locationId);
		this.name = name;
		this.style = style;
		this.category = category;
		this.abv = abv;
		this.ibu = ibu;
	}

}
