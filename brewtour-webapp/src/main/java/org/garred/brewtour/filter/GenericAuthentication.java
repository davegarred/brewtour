package org.garred.brewtour.filter;

import java.util.Collection;

import org.garred.brewtour.application.UserId;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
public class GenericAuthentication implements Authentication {

	private final GenericUserDetails principal;

	public GenericAuthentication(UserId userId) {
		this.principal = new GenericUserDetails(userId);
	}
	@Override
	public String getName() {
		return this.principal.getUsername();
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.principal.getAuthorities();
	}
	@Override
	public Object getCredentials() {
		return null;
	}
	@Override
	public Object getDetails() {
		return null;
	}
	@Override
	public Object getPrincipal() {
		return this.principal;
	}
	@Override
	public boolean isAuthenticated() {
		return true;
	}
	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
	}

}
