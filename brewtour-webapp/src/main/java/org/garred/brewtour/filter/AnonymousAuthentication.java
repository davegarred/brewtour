package org.garred.brewtour.filter;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
public class AnonymousAuthentication implements Authentication {

	private final AnonymousUserDetails principal;

	public AnonymousAuthentication(UUID uuid) {
		this.principal = new AnonymousUserDetails(uuid);
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
