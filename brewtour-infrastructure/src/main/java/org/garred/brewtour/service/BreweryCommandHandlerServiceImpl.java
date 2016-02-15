package org.garred.brewtour.service;

import org.garred.brewtour.application.IdentifierFactory;
import org.garred.brewtour.domain.BreweryId;

public class BreweryCommandHandlerServiceImpl implements BreweryCommandHandlerService {

	private final IdentifierFactory<BreweryId> identifierFactory;

	public BreweryCommandHandlerServiceImpl(IdentifierFactory<BreweryId> identifierFactory) {
		this.identifierFactory = identifierFactory;
	}

	@Override
	public BreweryId nextBreweryId() {
		return this.identifierFactory.next();
	}

}
