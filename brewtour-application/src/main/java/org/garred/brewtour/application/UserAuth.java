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
	private final Set<String> authorities;

	@JsonCreator
	public UserAuth(@JsonProperty("identifier") UserId identifier,
			@JsonProperty("login") String login,
			@JsonProperty("roles") Set<String> authorities) {
		super(identifier);
		this.login = login;
		this.authorities = authorities;
	}

	public String getLogin() {
		return this.login;
	}

	public Set<String> getAuthorities() {
		return this.authorities;
	}

	public static UserAuth userAuthorization(UserId userId, String login, Set<String> authorities) {
		return new UserAuth(userId, login, authorities);
	}
	public static UserAuth guest(String id) {
		return new UserAuth(new UserId(id), null, Collections.emptySet());
	}

	public UserAuth discover(Set<String> newAuthorities) {
		this.authorities.addAll(newAuthorities);
		return this;
	}

	public boolean hasAuthority(String authority) {
		return this.authorities != null && this.authorities.contains(authority);
	}

	public boolean hasAnyAuthority(Collection<String> checkAuthorities) {
		if(this.authorities == null || this.authorities.isEmpty() || checkAuthorities == null) {
			return false;
		}
		for(final String authority : checkAuthorities) {
			if(this.authorities.contains(authority)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.authorities == null) ? 0 : this.authorities.hashCode());
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
		if (this.authorities == null) {
			if (other.authorities != null)
				return false;
		} else if (!this.authorities.equals(other.authorities))
			return false;
		return true;
	}

}
