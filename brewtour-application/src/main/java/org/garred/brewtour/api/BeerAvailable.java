package org.garred.brewtour.api;

import org.garred.brewtour.application.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerAvailable {

	public final LocationId locationId;
	public final String name;

	@JsonCreator
	public BeerAvailable(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("name") String name) {
		this.locationId = locationId;
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		result = prime * result + ((this.locationId == null) ? 0 : this.locationId.hashCode());
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
		final BeerAvailable other = (BeerAvailable) obj;
		if (this.name == null) {
			if (other.name != null)
				return false;
		} else if (!this.name.equals(other.name))
			return false;
		if (this.locationId == null) {
			if (other.locationId != null)
				return false;
		} else if (!this.locationId.equals(other.locationId))
			return false;
		return true;
	}
}
