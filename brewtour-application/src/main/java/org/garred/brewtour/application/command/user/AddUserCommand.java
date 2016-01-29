package org.garred.brewtour.application.command.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddUserCommand extends AbstractUserFiredCommand {

	public final String login;

	@JsonCreator
	public AddUserCommand(@JsonProperty("login") String login) {
        this.login = login;
	}

}