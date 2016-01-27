package org.garred.brewtour.application.cqrs;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.garred.brewtour.api.AddBeerCommand;
import org.garred.brewtour.api.AddBeerReviewCommand;
import org.garred.brewtour.api.AddLocationCommand;
import org.garred.brewtour.api.AddLocationReviewCommand;
import org.garred.brewtour.api.AddPopulatedLocationCommand;
import org.garred.brewtour.api.BeerAvailableCommand;
import org.garred.brewtour.api.BeerUnavailableCommand;
import org.garred.brewtour.api.ModifyBeerCommand;
import org.garred.brewtour.application.LocationId;

public class LocationCommandHandler extends AbstractCommandHandler<LocationId,Location> {

	private final IdentifierFactory<LocationId> identifierFactory;

	public LocationCommandHandler(Repository<Location> repository, IdentifierFactory<LocationId> identifierFactory) {
		super(repository);
		this.identifierFactory = identifierFactory;
	}


	@CommandHandler
	public void handle(AddPopulatedLocationCommand command) {
		this.repository.add(Location.addPopulatedLocation(this.identifierFactory.next(), command));
	}
	@CommandHandler
    public void handle(AddLocationCommand command) {
		this.repository.add(Location.addLocation(this.identifierFactory.next(), command));
    }

	@CommandHandler
	public void handle(AddBeerCommand command) {
		require(command).addBeer(command);
	}

	@CommandHandler
	public void handle(ModifyBeerCommand command) {
		require(command).modifyBeer(command);
	}
	@CommandHandler
	public void handle(BeerUnavailableCommand command) {
		require(command).beerUnavailable(command);
	}
	@CommandHandler
	public void handle(BeerAvailableCommand command) {
		require(command).beerAvailable(command);
	}

	@CommandHandler
	public void handle(AddLocationReviewCommand command) {
		require(command).addLocationReview(command);
	}
	@CommandHandler
	public void handle(AddBeerReviewCommand command) {
		require(command).addBeerReview(command);
	}


}
