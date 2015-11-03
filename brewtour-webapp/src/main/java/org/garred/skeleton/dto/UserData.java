package org.garred.skeleton.dto;

import org.springframework.util.Assert;

public class UserData {

	public static final UserData NO_USER = new UserData(null);
	
	public final String username;
	
	private UserData(String username) {
		this.username = username;
	}
	
	public static UserData userData(String username) {
		Assert.hasLength(username);
		return new UserData(username);
	}
	
}
