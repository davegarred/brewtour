package org.garred.brewtour.application.event.user;

import org.garred.brewtour.application.command.user.AddUserCommand;
import org.garred.brewtour.domain.UserId;

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
