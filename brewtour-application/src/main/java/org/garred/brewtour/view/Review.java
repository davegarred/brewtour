package org.garred.brewtour.view;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = NON_NULL)
public class Review extends AbstractView {

	public final String userScreenName;
	public final String medal;
	public final String review;
	@JsonCreator
	public Review(
			@JsonProperty("userScreenName") String userScreenName,
			@JsonProperty("medal") String medal,
			@JsonProperty("review") String review) {
		this.userScreenName = userScreenName;
		this.medal = medal;
		this.review = review;
	}

}
