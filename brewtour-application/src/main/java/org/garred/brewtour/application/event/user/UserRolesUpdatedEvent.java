package org.garred.brewtour.application.event.user;

import java.util.Set;

import org.garred.brewtour.domain.UserId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRolesUpdatedEvent extends AbstractUserEvent {

	public final Set<String> roles;

	@JsonCreator
	public UserRolesUpdatedEvent(@JsonProperty("userId") UserId userId,
			@JsonProperty("roles") Set<String> roles) {
		super(userId);
		this.roles = roles;
	}

}
