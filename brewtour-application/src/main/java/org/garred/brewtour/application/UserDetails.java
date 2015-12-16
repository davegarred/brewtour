package org.garred.brewtour.application;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDetails extends AbstractEntity<UserId>{

	private final Set<LocationId> favoriteLocations;

	@JsonCreator
	public UserDetails(@JsonProperty("identifier") UserId identifier,
			@JsonProperty("favoriteLocations") Set<LocationId> favoriteLocations) {
		super(identifier);
		this.favoriteLocations = favoriteLocations;
	}

	public Set<LocationId> getFavoriteLocations() {
		return this.favoriteLocations;
	}

	public static UserDetails userDetails(UserId userId, HashSet<LocationId> locations) {
		return new UserDetails(userId, locations);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.favoriteLocations == null) ? 0 : this.favoriteLocations.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final UserDetails other = (UserDetails) obj;
		if (this.favoriteLocations == null) {
			if (other.favoriteLocations != null)
				return false;
		} else if (!this.favoriteLocations.equals(other.favoriteLocations))
			return false;
		return true;
	}

}
