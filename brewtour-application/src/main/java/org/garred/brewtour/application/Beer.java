package org.garred.brewtour.application;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Beer extends AbstractObject {

	private final String brewDbId,name,status,style,category;
	private final BigDecimal abv;

	@JsonCreator
	public Beer(@JsonProperty("brewDbId") String brewDbId,
			@JsonProperty("name") String name,
			@JsonProperty("status") String status,
			@JsonProperty("style") String style,
			@JsonProperty("category") String category,
			@JsonProperty("abv") BigDecimal abv) {
		this.brewDbId = brewDbId;
		this.name = name;
		this.status = status;
		this.style = style;
		this.category = category;
		this.abv = abv;
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

	public String getStyle() {
		return this.style;
	}

	public String getCategory() {
		return this.category;
	}

	public BigDecimal getAbv() {
		return this.abv;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.abv == null) ? 0 : this.abv.hashCode());
		result = prime * result + ((this.brewDbId == null) ? 0 : this.brewDbId.hashCode());
		result = prime * result + ((this.category == null) ? 0 : this.category.hashCode());
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
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
		if (this.name == null) {
			if (other.name != null)
				return false;
		} else if (!this.name.equals(other.name))
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
