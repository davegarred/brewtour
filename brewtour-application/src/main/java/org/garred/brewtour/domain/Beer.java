package org.garred.brewtour.domain;

import static java.math.RoundingMode.HALF_UP;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Beer extends AbstractObject {

	private final String brewDbId,name,status;
	private String style,category;
	private BigDecimal abv, ibu;
	private boolean available;
	private final List<Review> reviews;

	public List<Review> getReviews() {
		return this.reviews;
	}

	private BigDecimal averageStars;

	@JsonCreator
	public Beer(@JsonProperty("brewDbId") String brewDbId,
			@JsonProperty("name") String name,
			@JsonProperty("status") String status,
			@JsonProperty("style") String style,
			@JsonProperty("category") String category,
			@JsonProperty("abv") BigDecimal abv,
			@JsonProperty("ibu") BigDecimal ibu,
			@JsonProperty("available") boolean available,
			@JsonProperty("reviews") List<Review> reviews) {
		this.brewDbId = brewDbId;
		this.name = name;
		this.status = status;
		this.style = style;
		this.category = category;
		this.abv = abv;
		this.ibu = ibu;
		this.available = available;
		this.reviews = reviews;
	}

	public void addReview(Review review) {
		this.reviews.add(review);
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

	public String getBrewDbId() {
		return this.brewDbId;
	}

	public String getName() {
		return this.name;
	}
	public String getStatus() {
		return this.status;
	}

	public BigDecimal getAverageStars() {
		return this.averageStars;
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
	public BigDecimal updateReviewAverage() {
		int count = 0;
		int totalStars = 0;
		for(final Review review : this.reviews) {
			count ++;
			totalStars += review.stars;
		}
		if(count > 0) {
			return new BigDecimal(((double)totalStars) / (double)count).setScale(1, HALF_UP);
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.abv == null) ? 0 : this.abv.hashCode());
		result = prime * result + (this.available ? 1231 : 1237);
		result = prime * result + ((this.brewDbId == null) ? 0 : this.brewDbId.hashCode());
		result = prime * result + ((this.category == null) ? 0 : this.category.hashCode());
		result = prime * result + ((this.ibu == null) ? 0 : this.ibu.hashCode());
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		result = prime * result + ((this.reviews == null) ? 0 : this.reviews.hashCode());
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
		if (this.brewDbId == null) {
			if (other.brewDbId != null)
				return false;
		} else if (!this.brewDbId.equals(other.brewDbId))
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
		if (this.reviews == null) {
			if (other.reviews != null)
				return false;
		} else if (!this.reviews.equals(other.reviews))
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
