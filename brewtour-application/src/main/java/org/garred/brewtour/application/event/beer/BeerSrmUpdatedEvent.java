package org.garred.brewtour.application.event.beer;

import java.math.BigDecimal;

import org.garred.brewtour.domain.BeerId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerSrmUpdatedEvent extends AbstractBeerEvent {

	public final BigDecimal srm;

	@JsonCreator
	public BeerSrmUpdatedEvent(@JsonProperty("beerId") BeerId beerId,
			@JsonProperty("srm") BigDecimal srm) {
		super(beerId);
		this.srm = srm;
	}

}
