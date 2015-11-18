package org.garred.brewtour.application;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDetails extends AbstractEntity<UserId>{

	private final String login;
	private final Set<LocationId> favoriteLocations;
	private final boolean admin;
	
	@JsonCreator
	public UserDetails(@JsonProperty("userid") UserId userId, 
			@JsonProperty("login") String login, 
			@JsonProperty("favoriteLocations") Set<LocationId> favoriteLocations, 
			@JsonProperty("admin") boolean admin) {
		super(userId);
		this.login = login;
		this.favoriteLocations = favoriteLocations;
		this.admin = admin;
	}
	
	public String getLogin() {
		return login;
	}

	public Set<LocationId> getFavoriteLocations() {
		return favoriteLocations;
	}
	
	public boolean isAdmin() {
		return admin;
	}

	public static UserDetails anonymousUserDetails(UserId userId, HashSet<LocationId> locations) {
		return new UserDetails(userId, "", locations, false);
	}

	public UserDetails discover(String discoveredLogin, boolean discoveredAdmin) {
		return new UserDetails(this.identifier, discoveredLogin, this.favoriteLocations, discoveredAdmin);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (admin ? 1231 : 1237);
		result = prime * result + ((favoriteLocations == null) ? 0 : favoriteLocations.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
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
		UserDetails other = (UserDetails) obj;
		if (admin != other.admin)
			return false;
		if (favoriteLocations == null) {
			if (other.favoriteLocations != null)
				return false;
		} else if (!favoriteLocations.equals(other.favoriteLocations))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}
}
