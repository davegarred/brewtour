package org.garred.brewtour.application.command.user;

import org.garred.brewtour.domain.UserId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddUserCommand extends AbstractUserCommand {

	public final String login;
	public final String password;

	@JsonCreator
	public AddUserCommand(@JsonProperty("userId") UserId userId,
			@JsonProperty("login") String login,
			@JsonProperty("password") String password) {
		super(userId);
        this.login = login;
        this.password = password;
	}

}
