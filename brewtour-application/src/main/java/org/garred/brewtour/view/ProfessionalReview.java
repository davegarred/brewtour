package org.garred.brewtour.view;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.math.BigDecimal;

import org.garred.brewtour.domain.ProfessionalRatingGroup;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = NON_NULL)
public class ProfessionalReview extends AbstractView {

	public final ProfessionalRatingGroup ratingGroup;
	public final BigDecimal rating;
	public final String link;
	@JsonCreator
	public ProfessionalReview(
			@JsonProperty("ratingGroup") ProfessionalRatingGroup ratingGroup,
			@JsonProperty("rating") BigDecimal rating,
			@JsonProperty("link") String link) {
		this.ratingGroup = ratingGroup;
		this.rating = rating;
		this.link = link;
	}

}
