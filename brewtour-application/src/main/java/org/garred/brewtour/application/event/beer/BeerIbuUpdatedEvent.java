package org.garred.brewtour.application.event.beer;

import java.math.BigDecimal;

import org.garred.brewtour.domain.BeerId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerIbuUpdatedEvent extends AbstractBeerEvent {

	public final BigDecimal ibu;

	@JsonCreator
	public BeerIbuUpdatedEvent(@JsonProperty("beerId") BeerId beerId,
			@JsonProperty("ibu") BigDecimal ibu) {
		super(beerId);
		this.ibu = ibu;
	}

}
