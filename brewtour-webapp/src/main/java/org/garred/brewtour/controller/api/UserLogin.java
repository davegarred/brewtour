package org.garred.brewtour.controller.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserLogin {

	public final String login;
	public final String password;

	@JsonCreator
	public UserLogin(@JsonProperty("login") String login,
			@JsonProperty("password") String password) {
        this.login = login;
        this.password = password;
	}


}
