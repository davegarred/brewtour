package org.garred.brewtour.application.event.location;

import java.math.BigDecimal;

import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerRatingUpdatedEvent extends AbstractLocationEvent {

	public final String beerName;
	public final BigDecimal description;

	@JsonCreator
	public BeerRatingUpdatedEvent(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("beerName") String beerName,
			@JsonProperty("rating") BigDecimal rating) {
		super(locationId);
		this.beerName = beerName;
		this.description = rating;
	}

}
