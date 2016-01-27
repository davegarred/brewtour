package org.garred.brewtour.api;

import org.garred.brewtour.application.UserId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddRoleToUserCommand extends AbstractUserCommand {

	public final String role;

	@JsonCreator
	public AddRoleToUserCommand(@JsonProperty("userId") UserId userId,
			@JsonProperty("role") String role) {
		super(userId);
		this.role = role;
	}

}
