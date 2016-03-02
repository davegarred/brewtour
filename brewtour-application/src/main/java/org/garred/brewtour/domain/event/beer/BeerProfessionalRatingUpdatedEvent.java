package org.garred.brewtour.domain.event.beer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.ProfessionalRatingGroup;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerProfessionalRatingUpdatedEvent extends AbstractBeerEvent {

	public final ProfessionalRatingGroup ratingGroup;
	public final String link;
    public final BigDecimal rating;
    public final BigDecimal ratingMax;
    public final LocalDateTime time;

	@JsonCreator
	public BeerProfessionalRatingUpdatedEvent(@JsonProperty("beerId") BeerId beerId,
			@JsonProperty("ratingGroup") ProfessionalRatingGroup ratingGroup,
			@JsonProperty("link") String link,
			@JsonProperty("rating") BigDecimal rating,
			@JsonProperty("ratingMax") BigDecimal ratingMax,
			@JsonProperty("time") LocalDateTime time
			) {
		super(beerId);
		this.ratingGroup = ratingGroup;
		this.link = link;
		this.rating = rating;
		this.ratingMax = ratingMax;
		this.time = time;
	}

}
