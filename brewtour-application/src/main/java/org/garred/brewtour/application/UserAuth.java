package org.garred.brewtour.application;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserAuth extends AbstractEntity<UserId>{

	public static final String ADMIN_ROLE = "ADMIN";
	public static final String TEST_ROLE = "TEST";

	private final String login;
	private final Set<String> roles;

	@JsonCreator
	public UserAuth(@JsonProperty("identifier") UserId identifier,
			@JsonProperty("login") String login,
			@JsonProperty("roles") Set<String> roles) {
		super(identifier);
		this.login = login;
		this.roles = roles;
	}

	public String getLogin() {
		return this.login;
	}

	public Set<String> getRoles() {
		return this.roles;
	}

	public static UserAuth userAuthorization(UserId userId, String login, Set<String> authorities) {
		return new UserAuth(userId, login, authorities);
	}
	public static UserAuth guest(String id) {
		return new UserAuth(new UserId(id), null, Collections.emptySet());
	}

	public UserAuth discover(Set<String> newAuthorities) {
		this.roles.addAll(newAuthorities);
		return this;
	}

	public boolean hasRole(String role) {
		return this.roles != null && this.roles.contains(role);
	}

	public boolean hasAnyRole(Collection<String> checkRoles) {
		if(this.roles == null || this.roles.isEmpty() || checkRoles == null) {
			return false;
		}
		for(final String role : checkRoles) {
			if(this.roles.contains(role)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.roles == null) ? 0 : this.roles.hashCode());
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
		final UserAuth other = (UserAuth) obj;
		if (this.roles == null) {
			if (other.roles != null)
				return false;
		} else if (!this.roles.equals(other.roles))
			return false;
		return true;
	}

}
