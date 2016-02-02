package org.garred.brewtour.domain;

import java.time.LocalDateTime;

public class UserReview extends AbstractObject {

	public final UserId userId;
	public final int stars;
	public final LocalDateTime time;
	public final String review;

	public UserReview(UserId userId, int stars, LocalDateTime time, String review) {
		this.userId = userId;
		this.stars = stars;
		this.time = time;
		this.review = review;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.review == null) ? 0 : this.review.hashCode());
		result = prime * result + this.stars;
		result = prime * result + ((this.time == null) ? 0 : this.time.hashCode());
		result = prime * result + ((this.userId == null) ? 0 : this.userId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final UserReview other = (UserReview) obj;
		if (this.review == null) {
			if (other.review != null)
				return false;
		} else if (!this.review.equals(other.review))
			return false;
		if (this.stars != other.stars)
			return false;
		if (this.time == null) {
			if (other.time != null)
				return false;
		} else if (!this.time.equals(other.time))
			return false;
		if (this.userId == null) {
			if (other.userId != null)
				return false;
		} else if (!this.userId.equals(other.userId))
			return false;
		return true;
	}
}
