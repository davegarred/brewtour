package org.garred.brewtour.domain.event.user;

import org.garred.brewtour.domain.Hash;
import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.domain.command.user.AddUserCommand;

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

	public static UserAddedEvent fromCommand(AddUserCommand command, UserId userId) {
		return new UserAddedEvent(userId, command.screenName, command.login, Hash.hashFromPassword(userId, command.password));
	}

}
