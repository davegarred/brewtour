package org.garred.brewtour.application.command.beer;

import java.math.BigDecimal;

import org.garred.brewtour.domain.BeerId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ModifyBeerCommand extends AbstractBeerCommand {

	public final String style,category;
	public final BigDecimal abv,ibu;

	@JsonCreator
	public ModifyBeerCommand(@JsonProperty("beerId") BeerId beerId,
			@JsonProperty("style") String style,
			@JsonProperty("category") String category,
			@JsonProperty("abv") BigDecimal abv,
			@JsonProperty("ibu") BigDecimal ibu) {
		super(beerId);
		this.style = style;
		this.category = category;
		this.abv = abv;
		this.ibu = ibu;
	}

}
