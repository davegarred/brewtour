package org.garred.brewtour.domain.event.beer;

import org.garred.brewtour.domain.BeerId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerStyleUpdatedEvent extends AbstractBeerEvent {

	public final String style;

	@JsonCreator
	public BeerStyleUpdatedEvent(@JsonProperty("beerId") BeerId beerId,
			@JsonProperty("style") String style) {
		super(beerId);
		this.style = style;
	}

}
