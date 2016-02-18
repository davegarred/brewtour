package org.garred.brewtour.application;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.garred.brewtour.application.command.user.AddRoleToUserCommand;
import org.garred.brewtour.application.command.user.AddUserCommand;
import org.garred.brewtour.application.command.user.RemoveRoleFromUserCommand;
import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.service.UserCommandHandlerService;

public class UserCommandHandler extends AbstractCommandHandler<UserId,User> {

	private final UserCommandHandlerService userCommandHandlerService;

	public UserCommandHandler(Repository<User> repository, UserCommandHandlerService userCommandHandlerService) {
		super(repository);
		this.userCommandHandlerService = userCommandHandlerService;
	}

	@CommandHandler
	public void handle(AddUserCommand command) {
		final UserId userId = this.userCommandHandlerService.randomUserId();
		this.repository.add(User.addUser(command, userId));
		command.identified(userId);
	}

	@CommandHandler
	public void handle(AddRoleToUserCommand command) {
		require(command).addRole(command);
	}
	@CommandHandler
	public void handle(RemoveRoleFromUserCommand command) {
		require(command).removeRole(command);
	}

//	private User getOrCreate(AbstractUserCommand command) {
//		final AbstractIdentifier identifier = command.identifier();
//		final User aggregate = this.repository.load(identifier);
//		if(aggregate == null) {
//			this.repository.add(User.addUser(command.identifier()));
//		}
//		return aggregate;
//	}
}
