package org.garred.brewtour.application;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Beer extends AbstractObject {

	private final String brewDbId;

	@JsonCreator
	public Beer(@JsonProperty("brewDbId") String brewDbId) {
		this.brewDbId = brewDbId;
	}

	public String getBrewDbId() {
		return this.brewDbId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.brewDbId == null) ? 0 : this.brewDbId.hashCode());
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
		if (this.brewDbId == null) {
			if (other.brewDbId != null)
				return false;
		} else if (!this.brewDbId.equals(other.brewDbId))
			return false;
		return true;
	}
}
