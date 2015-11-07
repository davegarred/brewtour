package org.garred.brewtour.application;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Locale extends AbstractEntity<LocaleId> {

	public final LocaleId localeId;
	public final String name;
	public final BigDecimal latitude;
	public final BigDecimal longitude;
	public final int zoom;
	public final List<LocalePoint> locations;
	
	public Locale(@JsonProperty("locationId") LocaleId localeId, 
			@JsonProperty("name") String name, 
			@JsonProperty("latitude") BigDecimal latitude, 
			@JsonProperty("longitude") BigDecimal longitude, 
			@JsonProperty("zoom") int zoom,
			@JsonProperty("locations") List<LocalePoint> locations) {
		super();
		this.localeId = localeId;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.zoom = zoom;
		this.locations = locations;
	}

	@Override
	public LocaleId identifier() {
		return this.localeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((localeId == null) ? 0 : localeId.hashCode());
		result = prime * result + ((locations == null) ? 0 : locations.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + zoom;
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
		Locale other = (Locale) obj;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (localeId == null) {
			if (other.localeId != null)
				return false;
		} else if (!localeId.equals(other.localeId))
			return false;
		if (locations == null) {
			if (other.locations != null)
				return false;
		} else if (!locations.equals(other.locations))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (zoom != other.zoom)
			return false;
		return true;
	}
}
