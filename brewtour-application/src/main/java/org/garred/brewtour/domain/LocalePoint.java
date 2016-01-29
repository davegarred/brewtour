package org.garred.brewtour.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LocalePoint extends AbstractObject {

	public final LocationId locationId;
	public final String name;
	public BigDecimal latitude;
	public BigDecimal longitude;
	public AvailableImages images;

	@JsonCreator
	public LocalePoint(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("name") String name,
			@JsonProperty("latitude") BigDecimal latitude,
			@JsonProperty("longitude") BigDecimal longitude,
			@JsonProperty("images") AvailableImages images) {
		this.locationId = locationId;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.images = images;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.images == null) ? 0 : this.images.hashCode());
		result = prime * result + ((this.latitude == null) ? 0 : this.latitude.hashCode());
		result = prime * result + ((this.locationId == null) ? 0 : this.locationId.hashCode());
		result = prime * result + ((this.longitude == null) ? 0 : this.longitude.hashCode());
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
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
		final LocalePoint other = (LocalePoint) obj;
		if (this.images == null) {
			if (other.images != null)
				return false;
		} else if (!this.images.equals(other.images))
			return false;
		if (this.latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!this.latitude.equals(other.latitude))
			return false;
		if (this.locationId == null) {
			if (other.locationId != null)
				return false;
		} else if (!this.locationId.equals(other.locationId))
			return false;
		if (this.longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!this.longitude.equals(other.longitude))
			return false;
		if (this.name == null) {
			if (other.name != null)
				return false;
		} else if (!this.name.equals(other.name))
			return false;
		return true;
	}
}
