package org.garred.brewtour.domain.event.beer;

import java.math.BigDecimal;

import org.garred.brewtour.domain.BeerId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerAbvUpdatedEvent extends AbstractBeerEvent {

	public final BigDecimal abv;

	@JsonCreator
	public BeerAbvUpdatedEvent(@JsonProperty("beerId") BeerId beerId,
			@JsonProperty("abv") BigDecimal abv) {
		super(beerId);
		this.abv = abv;
	}

}
