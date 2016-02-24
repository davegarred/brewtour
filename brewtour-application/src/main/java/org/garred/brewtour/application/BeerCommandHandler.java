package org.garred.brewtour.application;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.garred.brewtour.application.command.beer.AddBeerCommand;
import org.garred.brewtour.application.command.beer.AddBeerRatingCommand;
import org.garred.brewtour.application.command.beer.AddBeerReviewCommand;
import org.garred.brewtour.application.command.beer.UpdateBeerAbvCommand;
import org.garred.brewtour.application.command.beer.UpdateBeerDescriptionCommand;
import org.garred.brewtour.application.command.beer.UpdateBeerIbuCommand;
import org.garred.brewtour.application.command.beer.UpdateBeerImagesCommand;
import org.garred.brewtour.application.command.beer.UpdateBeerSrmCommand;
import org.garred.brewtour.application.command.beer.UpdateBeerStyleCommand;
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
		final BeerId beerId = this.service.nextBeerId();
		this.repository.add(Beer.addBeer(beerId, command));
		command.identified(beerId);
    }



	@CommandHandler
	public void handle(UpdateBeerDescriptionCommand command) {
		require(command).modifyDescription(command.description);
	}
	@CommandHandler
	public void handle(UpdateBeerStyleCommand command) {
		require(command).modifyStyle(command.style);
	}
	@CommandHandler
	public void handle(UpdateBeerAbvCommand command) {
		require(command).modifyAbv(command.abv);
	}
	@CommandHandler
	public void handle(UpdateBeerIbuCommand command) {
		require(command).modifyIbu(command.ibu);
	}
	@CommandHandler
	public void handle(UpdateBeerSrmCommand command) {
		require(command).modifySrm(command.srm);
	}
	@CommandHandler
	public void handle(UpdateBeerImagesCommand command) {
		require(command).updateImages(command);
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
