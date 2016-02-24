package org.garred.brewtour.application.command.user;

import static org.garred.brewtour.view.UserAuthView.ADMIN_ROLE;

import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.security.SecuredCommand;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@SecuredCommand(ADMIN_ROLE)
public class RemoveRoleFromUserCommand extends AbstractUserCommand {

	public final String role;

	@JsonCreator
	public RemoveRoleFromUserCommand(@JsonProperty("userId") UserId userId,
			@JsonProperty("role") String role) {
		super(userId);
		this.role = role;
	}

}
