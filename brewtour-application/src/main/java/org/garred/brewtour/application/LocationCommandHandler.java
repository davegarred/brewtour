package org.garred.brewtour.application;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.garred.brewtour.application.command.location.AddLocationCommand;
import org.garred.brewtour.application.command.location.AddLocationCommentCommand;
import org.garred.brewtour.application.command.location.AddLocationRatingCommand;
import org.garred.brewtour.application.command.location.AddLocationReviewCommand;
import org.garred.brewtour.application.command.location.BeerAvailableCommand;
import org.garred.brewtour.application.command.location.BeerUnavailableCommand;
import org.garred.brewtour.application.command.location.UpdateLocationAddressCommand;
import org.garred.brewtour.application.command.location.UpdateLocationBreweryAssociationCommand;
import org.garred.brewtour.application.command.location.UpdateLocationDescriptionCommand;
import org.garred.brewtour.application.command.location.UpdateLocationHoursOfOperationCommand;
import org.garred.brewtour.application.command.location.UpdateLocationImagesCommand;
import org.garred.brewtour.application.command.location.UpdateLocationPhoneCommand;
import org.garred.brewtour.application.command.location.UpdateLocationPositionCommand;
import org.garred.brewtour.application.command.location.UpdateLocationWebsiteCommand;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.security.UserAuth;
import org.garred.brewtour.security.UserHolder;
import org.garred.brewtour.service.LocationCommandHandlerService;

public class LocationCommandHandler extends AbstractCommandHandler<LocationId,Location> {

	private final LocationCommandHandlerService service;

	public LocationCommandHandler(Repository<Location> repository, LocationCommandHandlerService service) {
		super(repository);
		this.service = service;
	}


	@CommandHandler
    public void handle(AddLocationCommand command) {
		final LocationId locationId = this.service.nextLocationId();
		this.repository.add(Location.addLocation(locationId, command));
		command.identified(locationId);
    }

	@CommandHandler
	public void handle(UpdateLocationAddressCommand command) {
		require(command).updateAddress(command);
	}
	@CommandHandler
	public void handle(UpdateLocationDescriptionCommand command) {
		require(command).updateDescription(command);
	}
	@CommandHandler
	public void handle(UpdateLocationBreweryAssociationCommand command) {
		require(command).associateWithBrewery(command.associatedBreweryId);
	}
	@CommandHandler
	public void handle(UpdateLocationHoursOfOperationCommand command) {
		require(command).updateHoursOfOperation(command);
	}
	@CommandHandler
	public void handle(UpdateLocationImagesCommand command) {
		require(command).updateImages(command);
	}
	@CommandHandler
	public void handle(UpdateLocationPhoneCommand command) {
		require(command).updatePhoneNumber(command);
	}
	@CommandHandler
	public void handle(UpdateLocationPositionCommand command) {
		require(command).updatePosition(command);
	}
	@CommandHandler
	public void handle(UpdateLocationWebsiteCommand command) {
		require(command).updateWebsite(command);
	}

	@CommandHandler
	public void handle(BeerAvailableCommand command) {
		require(command).beerAvailable(command);
	}
	@CommandHandler
	public void handle(BeerUnavailableCommand command) {
		require(command).beerUnavailable(command);
	}

	// user fired commands
	@CommandHandler
	public void handle(AddLocationCommentCommand command) {
		require(command).addComment(command, user(), this.service.now());
	}
	@CommandHandler
	public void handle(AddLocationRatingCommand command) {
		require(command).addLocationStarRating(command, user());
	}
	@CommandHandler
	public void handle(AddLocationReviewCommand command) {
		require(command).addLocationReview(command, user(), this.service.now());
	}


	private static UserAuth user() {
		final UserAuth userAuth = UserHolder.get();
		if(userAuth == null) {
			throw new RuntimeException("No user!");
		}
		return userAuth;
	}


}
