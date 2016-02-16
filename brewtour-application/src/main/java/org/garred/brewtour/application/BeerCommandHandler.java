package org.garred.brewtour.application;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.garred.brewtour.application.command.beer.AddBeerCommand;
import org.garred.brewtour.application.command.beer.AddBeerRatingCommand;
import org.garred.brewtour.application.command.beer.AddBeerReviewCommand;
import org.garred.brewtour.application.command.beer.ModifyBeerCommand;
import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.security.UserAuth;
import org.garred.brewtour.security.UserHolder;
import org.garred.brewtour.service.BeerCommandHandlerService;

public class BeerCommandHandler extends AbstractCommandHandler<BeerId,Beer> {

	private final BeerCommandHandlerService service;

	public BeerCommandHandler(Repository<Beer> repository, BeerCommandHandlerService service) {
		super(repository);
		this.service = service;
	}

	@CommandHandler
    public void handle(AddBeerCommand command) {
		this.repository.add(Beer.addBeer(this.service.nextBeerId(), command));
    }



	@CommandHandler
	public void handle(ModifyBeerCommand command) {
		require(command).modifyBeer(command);
	}

	// user fired commands
	@CommandHandler
	public void handle(AddBeerRatingCommand command) {
		require(command).addBeerStarRating(command, user());
	}
	@CommandHandler
	public void handle(AddBeerReviewCommand command) {
		require(command).addBeerReview(command, user(), this.service.now());
	}


	private static UserAuth user() {
		final UserAuth userAuth = UserHolder.get();
		if(userAuth == null) {
			throw new RuntimeException("No user!");
		}
		return userAuth;
	}


}
