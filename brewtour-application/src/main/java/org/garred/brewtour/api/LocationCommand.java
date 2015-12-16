package org.garred.brewtour.api;

import org.garred.brewtour.application.LocationId;

public class LocationCommand {

	public final LocationId locationId;

	public LocationCommand(LocationId locationId) {
		this.locationId = locationId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		final LocationCommand other = (LocationCommand) obj;
		if (this.locationId == null) {
			if (other.locationId != null)
				return false;
		} else if (!this.locationId.equals(other.locationId))
			return false;
		return true;
	}

}
