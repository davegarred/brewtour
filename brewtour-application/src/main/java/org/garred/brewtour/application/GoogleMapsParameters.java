package org.garred.brewtour.application;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GoogleMapsParameters {

	public final GoogleMapsPosition center;
	public final int zoom;
	
	@JsonCreator
	public GoogleMapsParameters(@JsonProperty("center") GoogleMapsPosition center,
			@JsonProperty("zoom") int zoom) {
		this.center = center;
		this.zoom = zoom;
	}

	public static class GoogleMapsPosition {
		public final BigDecimal lat;
		public final BigDecimal lng;

		@JsonCreator
		public GoogleMapsPosition(
				@JsonProperty("latitude") BigDecimal latitude, 
				@JsonProperty("longitude") BigDecimal longitude) {
			this.lat = latitude;
			this.lng = longitude;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((center == null) ? 0 : center.hashCode());
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
		GoogleMapsParameters other = (GoogleMapsParameters) obj;
		if (center == null) {
			if (other.center != null)
				return false;
		} else if (!center.equals(other.center))
			return false;
		if (zoom != other.zoom)
			return false;
		return true;
	}
}
