package org.garred.brewtour.application.event.beer;

import org.garred.brewtour.domain.BeerId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerDescriptionUpdatedEvent extends AbstractBeerEvent {

	public final String description;

	@JsonCreator
	public BeerDescriptionUpdatedEvent(@JsonProperty("beerId") BeerId beerId,
			@JsonProperty("description") String description) {
		super(beerId);
		this.description = description;
	}

}
