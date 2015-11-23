package org.garred.brewtour.filter;

import java.util.Collection;
import java.util.Collections;

import org.garred.brewtour.application.UserId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
public class GenericUserDetails implements UserDetails {

	private final UserId userId;

	public GenericUserDetails(UserId userId) {
		this.userId = userId;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptySet();
	}
	@Override
	public String getPassword() {
		return null;
	}
	@Override
	public String getUsername() {
		return this.userId.toString();
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}

}
