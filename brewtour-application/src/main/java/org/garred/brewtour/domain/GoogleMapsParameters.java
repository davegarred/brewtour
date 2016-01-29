package org.garred.brewtour.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GoogleMapsParameters extends AbstractObject {

	public final GoogleMapsPosition center;
	public final int zoom;

	@JsonCreator
	public GoogleMapsParameters(@JsonProperty("center") GoogleMapsPosition center,
			@JsonProperty("zoom") int zoom) {
		this.center = center;
		this.zoom = zoom;
	}

	public static class GoogleMapsPosition extends AbstractObject {
		public final BigDecimal lat;
		public final BigDecimal lng;

		@JsonCreator
		public GoogleMapsPosition(
				@JsonProperty("latitude") BigDecimal latitude,
				@JsonProperty("longitude") BigDecimal longitude) {
			this.lat = latitude;
			this.lng = longitude;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((this.lat == null) ? 0 : this.lat.hashCode());
			result = prime * result + ((this.lng == null) ? 0 : this.lng.hashCode());
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
			final GoogleMapsPosition other = (GoogleMapsPosition) obj;
			if (this.lat == null) {
				if (other.lat != null)
					return false;
			} else if (!this.lat.equals(other.lat))
				return false;
			if (this.lng == null) {
				if (other.lng != null)
					return false;
			} else if (!this.lng.equals(other.lng))
				return false;
			return true;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.center == null) ? 0 : this.center.hashCode());
		result = prime * result + this.zoom;
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
		final GoogleMapsParameters other = (GoogleMapsParameters) obj;
		if (this.center == null) {
			if (other.center != null)
				return false;
		} else if (!this.center.equals(other.center))
			return false;
		if (this.zoom != other.zoom)
			return false;
		return true;
	}
}
