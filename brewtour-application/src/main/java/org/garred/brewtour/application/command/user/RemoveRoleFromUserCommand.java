package org.garred.brewtour.application.command.user;

import org.garred.brewtour.domain.UserId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RemoveRoleFromUserCommand extends AbstractUserCommand {

	public final String role;

	@JsonCreator
	public RemoveRoleFromUserCommand(@JsonProperty("userId") UserId userId,
			@JsonProperty("role") String role) {
		super(userId);
		this.role = role;
	}

}
