package org.garred.brewtour.service;

import java.time.LocalDateTime;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.IdentifierFactory;

public class BeerCommandHandlerServiceImpl implements BeerCommandHandlerService {

	private final IdentifierFactory<BeerId> identifierFactory;

	public BeerCommandHandlerServiceImpl(IdentifierFactory<BeerId> identifierFactory) {
		this.identifierFactory = identifierFactory;
	}

	@Override
	public BeerId nextBeerId() {
		return this.identifierFactory.next();
	}

	@Override
	public LocalDateTime now() {
		return LocalDateTime.now();
	}
}
