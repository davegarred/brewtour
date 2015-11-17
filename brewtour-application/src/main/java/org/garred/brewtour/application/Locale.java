package org.garred.brewtour.application;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Locale extends AbstractEntity<LocaleId> {

	public final String name;
	public final GoogleMapsParameters googleMapsParameters;
	public final List<LocalePoint> locations;

	@JsonCreator
	public Locale(@JsonProperty("locationId") LocaleId localeId,
			@JsonProperty("name") String name,
			@JsonProperty("googleMapsParameters") GoogleMapsParameters googleMapsParameters,
			@JsonProperty("locations") List<LocalePoint> locations) {
		super(localeId);
		this.name = name;
		this.googleMapsParameters = googleMapsParameters;
		this.locations = locations;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.googleMapsParameters == null) ? 0 : this.googleMapsParameters.hashCode());
		result = prime * result + ((this.locations == null) ? 0 : this.locations.hashCode());
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
		final Locale other = (Locale) obj;
		if (this.googleMapsParameters == null) {
			if (other.googleMapsParameters != null)
				return false;
		} else if (!this.googleMapsParameters.equals(other.googleMapsParameters))
			return false;
		if (this.locations == null) {
			if (other.locations != null)
				return false;
		} else if (!this.locations.equals(other.locations))
			return false;
		if (this.name == null) {
			if (other.name != null)
				return false;
		} else if (!this.name.equals(other.name))
			return false;
		return true;
	}
}
