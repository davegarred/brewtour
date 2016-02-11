package org.garred.brewtour.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Beer extends AbstractObject {

	private String name,status;
	private String style,category;
	private BigDecimal abv, ibu;
	private boolean available;
	private final List<UserReview> userReviews = new ArrayList<>();
	private final Map<UserId,ReviewMedal> userMedalRatings = new HashMap<>();

	public List<UserReview> getReviews() {
		return this.userReviews;
	}
	public Map<UserId,ReviewMedal> getUserMedalRatings() {
		return this.userMedalRatings;
	}

	private ReviewMedal medal;


	public static Beer fromEvent(String name, String status, String style, String category,
			BigDecimal abv, BigDecimal ibu, boolean available) {
		final Beer beer = new Beer();
		beer.name = name;
		beer.status = status;
		beer.style = style;
		beer.category = category;
		beer.abv = abv;
		beer.ibu = ibu;
		beer.available = available;
		return beer;
	}
	public void addReview(UserReview review) {
		this.userReviews.add(review);
	}

	public String getStyle() {
		return this.style;
	}
	public String getCategory() {
		return this.category;
	}
	public BigDecimal getAbv() {
		return this.abv;
	}
	public BigDecimal getIbu() {
		return this.ibu;
	}
	public boolean isAvailable() {
		return this.available;
	}

	public String getName() {
		return this.name;
	}
	public String getStatus() {
		return this.status;
	}

	public ReviewMedal getMedal() {
		return this.medal;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setAbv(BigDecimal abv) {
		this.abv = abv;
	}

	public void setIbu(BigDecimal ibu) {
		this.ibu = ibu;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public void setMedalRating(UserId userId, ReviewMedal medal) {
		this.userMedalRatings.put(userId, medal);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.abv == null) ? 0 : this.abv.hashCode());
		result = prime * result + (this.available ? 1231 : 1237);
		result = prime * result + ((this.category == null) ? 0 : this.category.hashCode());
		result = prime * result + ((this.ibu == null) ? 0 : this.ibu.hashCode());
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		result = prime * result + ((this.userReviews == null) ? 0 : this.userReviews.hashCode());
		result = prime * result + ((this.status == null) ? 0 : this.status.hashCode());
		result = prime * result + ((this.style == null) ? 0 : this.style.hashCode());
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
		final Beer other = (Beer) obj;
		if (this.abv == null) {
			if (other.abv != null)
				return false;
		} else if (!this.abv.equals(other.abv))
			return false;
		if (this.available != other.available)
			return false;
		if (this.category == null) {
			if (other.category != null)
				return false;
		} else if (!this.category.equals(other.category))
			return false;
		if (this.ibu == null) {
			if (other.ibu != null)
				return false;
		} else if (!this.ibu.equals(other.ibu))
			return false;
		if (this.name == null) {
			if (other.name != null)
				return false;
		} else if (!this.name.equals(other.name))
			return false;
		if (this.userReviews == null) {
			if (other.userReviews != null)
				return false;
		} else if (!this.userReviews.equals(other.userReviews))
			return false;
		if (this.status == null) {
			if (other.status != null)
				return false;
		} else if (!this.status.equals(other.status))
			return false;
		if (this.style == null) {
			if (other.style != null)
				return false;
		} else if (!this.style.equals(other.style))
			return false;
		return true;
	}
}
