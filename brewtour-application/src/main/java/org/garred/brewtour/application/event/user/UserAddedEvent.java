package org.garred.brewtour.application.event.user;

import org.garred.brewtour.application.command.user.AddUserCommand;
import org.garred.brewtour.domain.Hash;
import org.garred.brewtour.domain.UserId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserAddedEvent extends AbstractUserEvent {

	public final String screenName;
	public final String login;
	public final Hash hash;

	@JsonCreator
	public UserAddedEvent(@JsonProperty("userId") UserId userId,
			@JsonProperty("screenName") String screenName,
			@JsonProperty("login") String login,
			@JsonProperty("hash") Hash hash) {
		super(userId);
		this.screenName = screenName;
		this.login = login;
		this.hash = hash;
	}

	public static UserAddedEvent fromCommand(AddUserCommand command) {
		return new UserAddedEvent(command.identifier(), command.screenName, command.login, Hash.hashFromPassword(command.identifier(), command.password));
	}

}
