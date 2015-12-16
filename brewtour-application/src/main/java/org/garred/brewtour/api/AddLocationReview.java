package org.garred.brewtour.api;

import org.garred.brewtour.application.LocationId;
import org.garred.brewtour.application.Review;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddLocationReview extends LocationCommand {

	public final Review review;

	@JsonCreator
	public AddLocationReview(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("review") Review review) {
		super(locationId);
		this.review = review;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.review == null) ? 0 : this.review.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final AddLocationReview other = (AddLocationReview) obj;
		if (this.review == null) {
			if (other.review != null)
				return false;
		} else if (!this.review.equals(other.review))
			return false;
		return true;
	}
}
