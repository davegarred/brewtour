package org.garred.brewtour.application.command.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddUserCommand {

	public final String screenName;
	public final String login;
	public final String password;

	@JsonCreator
	public AddUserCommand(
			@JsonProperty("screenName") String screenName,
			@JsonProperty("login") String login,
			@JsonProperty("password") String password) {
		this.screenName = screenName;
        this.login = login;
        this.password = password;
	}

}
