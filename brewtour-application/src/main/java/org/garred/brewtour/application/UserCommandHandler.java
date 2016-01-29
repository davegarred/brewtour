package org.garred.brewtour.application;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.garred.brewtour.application.command.user.AddFavoriteLocationCommand;
import org.garred.brewtour.application.command.user.AddRoleToUserCommand;
import org.garred.brewtour.application.command.user.AddUserCommand;
import org.garred.brewtour.application.command.user.RemoveFavoriteLocationCommand;
import org.garred.brewtour.application.command.user.RemoveRoleFromUserCommand;
import org.garred.brewtour.domain.AbstractIdentifier;
import org.garred.brewtour.domain.UserId;

public class UserCommandHandler extends AbstractCommandHandler<UserId,User> {

	public UserCommandHandler(Repository<User> repository) {
		super(repository);
	}

	@CommandHandler
	public void handle(AddUserCommand command) {
		this.repository.add(User.addUser(command));
	}
	@CommandHandler
	public void handle(AddFavoriteLocationCommand command) {
		getOrCreate(command).addFavoriteLocation(command);
	}

	@CommandHandler
	public void handle(RemoveFavoriteLocationCommand command) {
		require(command).removeFavoriteLocation(command);
	}

	@CommandHandler
	public void handle(AddRoleToUserCommand command) {
		require(command).addRole(command);
	}
	@CommandHandler
	public void handle(RemoveRoleFromUserCommand command) {
		require(command).removeRole(command);
	}

	private User getOrCreate(AddFavoriteLocationCommand command) {
		final AbstractIdentifier identifier = command.identifier();
		final User aggregate = this.repository.load(identifier);
		if(aggregate == null) {
			this.repository.add(User.addUser(command.identifier()));
		}
		return aggregate;
	}
}
