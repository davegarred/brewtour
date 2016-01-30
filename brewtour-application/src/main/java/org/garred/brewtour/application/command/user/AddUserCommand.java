package org.garred.brewtour.application.command.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddUserCommand extends AbstractUserFiredCommand {

	public final String login;
	public final String password;

	@JsonCreator
	public AddUserCommand(@JsonProperty("login") String login,
			@JsonProperty("password") String password) {
        this.login = login;
        this.password = password;
	}

}
