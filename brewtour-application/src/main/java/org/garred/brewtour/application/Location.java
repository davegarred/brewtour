package org.garred.brewtour.application;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Location extends AbstractEntity<LocationId> {

	public final LocationId locationId;
	public final String brewDbId;
	public final String name;
	public final String description;
	public final BigDecimal lattitude;
	public final BigDecimal longitude;
	public final AvailableImages images;
	public final List<Beer> beers;

	@JsonCreator
	public Location(@JsonProperty("locationId") LocationId locationId,
			@JsonProperty("brewDbId") String brewDbId,
			@JsonProperty("name") String name,
			@JsonProperty("description") String description,
			@JsonProperty("latitude") BigDecimal latitude,
			@JsonProperty("longitude") BigDecimal longitude,
			@JsonProperty("images") AvailableImages images,
			@JsonProperty("beers") List<Beer> beers) {
		this.locationId = locationId;
		this.brewDbId = brewDbId;
		this.name = name;
		this.description = description;
		this.lattitude = latitude;
		this.longitude = longitude;
		this.images = images;
		this.beers = beers;
	}

	@Override
	public LocationId identifier() {
		return this.locationId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.beers == null) ? 0 : this.beers.hashCode());
		result = prime * result + ((this.brewDbId == null) ? 0 : this.brewDbId.hashCode());
		result = prime * result + ((this.description == null) ? 0 : this.description.hashCode());
		result = prime * result + ((this.images == null) ? 0 : this.images.hashCode());
		result = prime * result + ((this.lattitude == null) ? 0 : this.lattitude.hashCode());
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
		final Location other = (Location) obj;
		if (this.beers == null) {
			if (other.beers != null)
				return false;
		} else if (!this.beers.equals(other.beers))
			return false;
		if (this.brewDbId == null) {
			if (other.brewDbId != null)
				return false;
		} else if (!this.brewDbId.equals(other.brewDbId))
			return false;
		if (this.description == null) {
			if (other.description != null)
				return false;
		} else if (!this.description.equals(other.description))
			return false;
		if (this.images == null) {
			if (other.images != null)
				return false;
		} else if (!this.images.equals(other.images))
			return false;
		if (this.lattitude == null) {
			if (other.lattitude != null)
				return false;
		} else if (!this.lattitude.equals(other.lattitude))
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
