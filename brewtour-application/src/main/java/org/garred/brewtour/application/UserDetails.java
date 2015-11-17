package org.garred.brewtour.application;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDetails extends AbstractEntity<UserId>{

	private final Set<LocationId> favoriteLocations;
	
	@JsonCreator
	public UserDetails(@JsonProperty("userid") UserId userId, @JsonProperty("favoriteLocations") Set<LocationId> favoriteLocations) {
		super(userId);
		this.favoriteLocations = favoriteLocations;
	}
	
	public Set<LocationId> getFavoriteLocations() {
		return favoriteLocations;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((favoriteLocations == null) ? 0 : favoriteLocations.hashCode());
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
		UserDetails other = (UserDetails) obj;
		if (favoriteLocations == null) {
			if (other.favoriteLocations != null)
				return false;
		} else if (!favoriteLocations.equals(other.favoriteLocations))
			return false;
		return true;
	}
}
