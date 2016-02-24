package org.garred.brewtour.application.event.beer;

import java.math.BigDecimal;

import org.garred.brewtour.application.command.beer.ModifyBeerCommand;
import org.garred.brewtour.domain.BeerId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerModifiedEvent extends AbstractBeerEvent {

	public final String description,style,category;
	public final BigDecimal abv,ibu;

	@JsonCreator
	public BeerModifiedEvent(@JsonProperty("beerId") BeerId beerId,
			@JsonProperty("description") String description,
			@JsonProperty("style") String style,
			@JsonProperty("category") String category,
			@JsonProperty("abv") BigDecimal abv,
			@JsonProperty("ibu") BigDecimal ibu) {
		super(beerId);
		this.description = description;
		this.style = style;
		this.category = category;
		this.abv = abv;
		this.ibu = ibu;
	}

	public static BeerModifiedEvent fromCommand(ModifyBeerCommand command) {
		return new BeerModifiedEvent(command.beerId, command.description, command.style, command.category, command.abv, command.ibu);
	}
}
