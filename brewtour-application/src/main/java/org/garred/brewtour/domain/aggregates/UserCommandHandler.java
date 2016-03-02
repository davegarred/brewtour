package org.garred.brewtour.domain.aggregates;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.domain.command.user.AddFavoriteBeerCommand;
import org.garred.brewtour.domain.command.user.AddFavoriteLocationCommand;
import org.garred.brewtour.domain.command.user.AddRoleToUserCommand;
import org.garred.brewtour.domain.command.user.AddUserCommand;
import org.garred.brewtour.domain.command.user.RemoveFavoriteBeerCommand;
import org.garred.brewtour.domain.command.user.RemoveFavoriteLocationCommand;
import org.garred.brewtour.domain.command.user.RemoveRoleFromUserCommand;
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
	@CommandHandler
	public void handle(AddFavoriteLocationCommand command) {
		require(command).addFavoriteLocation(command.locationId);
	}
	@CommandHandler
	public void handle(RemoveFavoriteLocationCommand command) {
		require(command).removeFavoriteLocation(command.locationId);
	}
	@CommandHandler
	public void handle(AddFavoriteBeerCommand command) {
		require(command).addFavoriteBeer(command.beerId);
	}
	@CommandHandler
	public void handle(RemoveFavoriteBeerCommand command) {
		require(command).removeFavoriteBeer(command.beerId);
	}

}
