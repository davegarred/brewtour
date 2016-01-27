package org.garred.brewtour.application.event;

import org.garred.brewtour.api.AddUserCommand;
import org.garred.brewtour.application.UserId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserAddedEvent extends AbstractUserEvent {

	public final String login;

	@JsonCreator
	public UserAddedEvent(@JsonProperty("userId") UserId userId,
			@JsonProperty("login") String login) {
		super(userId);
		this.login = login;
	}

	public static UserAddedEvent fromCommand(AddUserCommand command) {
		return new UserAddedEvent(command.identifier(), command.login);
	}

}
