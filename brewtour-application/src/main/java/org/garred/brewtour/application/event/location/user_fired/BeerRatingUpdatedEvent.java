package org.garred.brewtour.application.event.location.user_fired;

import java.math.BigDecimal;

import org.garred.brewtour.application.event.location.AbstractLocationEvent;
import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerRatingUpdatedEvent extends AbstractLocationEvent {

	public final String beerName;
	public final BigDecimal rating;

	@JsonCreator
	public BeerRatingUpdatedEvent(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("beerName") String beerName,
			@JsonProperty("rating") BigDecimal rating) {
		super(locationId);
		this.beerName = beerName;
		this.rating = rating;
	}

}
