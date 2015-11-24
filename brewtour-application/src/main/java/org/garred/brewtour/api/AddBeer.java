package org.garred.brewtour.api;

import java.math.BigDecimal;

import org.garred.brewtour.application.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddBeer {

	public final LocationId locationId;
	public final String name,style,category;
	public final BigDecimal abv,ibu;

	@JsonCreator
	public AddBeer(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("name") String name,
			@JsonProperty("style") String style,
			@JsonProperty("category") String category,
			@JsonProperty("abv") BigDecimal abv,
			@JsonProperty("ibu") BigDecimal ibu) {
		this.locationId = locationId;
		this.name = name;
		this.style = style;
		this.category = category;
		this.abv = abv;
		this.ibu = ibu;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.abv == null) ? 0 : this.abv.hashCode());
		result = prime * result + ((this.category == null) ? 0 : this.category.hashCode());
		result = prime * result + ((this.ibu == null) ? 0 : this.ibu.hashCode());
		result = prime * result + ((this.locationId == null) ? 0 : this.locationId.hashCode());
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
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
		final AddBeer other = (AddBeer) obj;
		if (this.abv == null) {
			if (other.abv != null)
				return false;
		} else if (!this.abv.equals(other.abv))
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
		if (this.locationId == null) {
			if (other.locationId != null)
				return false;
		} else if (!this.locationId.equals(other.locationId))
			return false;
		if (this.name == null) {
			if (other.name != null)
				return false;
		} else if (!this.name.equals(other.name))
			return false;
		if (this.style == null) {
			if (other.style != null)
				return false;
		} else if (!this.style.equals(other.style))
			return false;
		return true;
	}

}
