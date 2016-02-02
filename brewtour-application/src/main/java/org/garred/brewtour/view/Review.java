package org.garred.brewtour.view;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = NON_NULL)
public class Review extends AbstractView {

	public final int stars;
	public final String review;
	@JsonCreator
	public Review(
			@JsonProperty("stars") int stars,
			@JsonProperty("review") String review) {
		this.stars = stars;
		this.review = review;
	}

}
