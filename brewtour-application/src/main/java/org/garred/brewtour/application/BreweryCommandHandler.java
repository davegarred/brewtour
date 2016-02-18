package org.garred.brewtour.application;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.garred.brewtour.application.command.brewery.AddBreweryCommand;
import org.garred.brewtour.domain.BreweryId;
import org.garred.brewtour.service.BreweryCommandHandlerService;

public class BreweryCommandHandler extends AbstractCommandHandler<BreweryId,Brewery> {

	private final BreweryCommandHandlerService service;

	public BreweryCommandHandler(Repository<Brewery> repository, BreweryCommandHandlerService service) {
		super(repository);
		this.service = service;
	}

	@CommandHandler
    public void handle(AddBreweryCommand command) {
		final BreweryId breweryId = this.service.nextBreweryId();
		this.repository.add(Brewery.addBrewery(breweryId, command.breweryName));
		command.identified(breweryId);
    }

}
