package org.garred.brewtour.security;

@SuppressWarnings("serial")
public class UserAuthorizationException extends RuntimeException {

	public UserAuthorizationException() {
		super("User does not have the required role for this action");
	}
}
