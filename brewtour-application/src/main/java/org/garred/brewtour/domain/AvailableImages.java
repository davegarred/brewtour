package org.garred.brewtour.domain;

import static org.garred.brewtour.domain.Image.NOT_AVAILABLE;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AvailableImages extends AbstractObject {

	public static final AvailableImages NO_IMAGES = new AvailableImages(NOT_AVAILABLE, NOT_AVAILABLE, NOT_AVAILABLE);

	public final Image icon;
	public final Image medium;
	public final Image large;

	@JsonCreator
	public AvailableImages(@JsonProperty("icon") Image icon, @JsonProperty("medium") Image medium, @JsonProperty("large") Image large) {
		this.icon = icon == null ? NOT_AVAILABLE : icon;
		this.medium = medium == null ? NOT_AVAILABLE : medium;
		this.large = large == null ? NOT_AVAILABLE : large;
	}

	public static AvailableImages fromString(String icon, String medium, String large) {
		return new AvailableImages(new Image(icon), new Image(medium), new Image(large));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.icon == null) ? 0 : this.icon.hashCode());
		result = prime * result + ((this.large == null) ? 0 : this.large.hashCode());
		result = prime * result + ((this.medium == null) ? 0 : this.medium.hashCode());
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
		final AvailableImages other = (AvailableImages) obj;
		if (this.icon == null) {
			if (other.icon != null)
				return false;
		} else if (!this.icon.equals(other.icon))
			return false;
		if (this.large == null) {
			if (other.large != null)
				return false;
		} else if (!this.large.equals(other.large))
			return false;
		if (this.medium == null) {
			if (other.medium != null)
				return false;
		} else if (!this.medium.equals(other.medium))
			return false;
		return true;
	}

}
