package org.garred.brewtour.service;

import java.time.LocalDateTime;

import org.garred.brewtour.application.IdentifierFactory;
import org.garred.brewtour.domain.LocationId;

public class LocationCommandHandlerServiceImpl implements LocationCommandHandlerService {

	private final IdentifierFactory<LocationId> identifierFactory;

	public LocationCommandHandlerServiceImpl(IdentifierFactory<LocationId> identifierFactory) {
		this.identifierFactory = identifierFactory;
	}

	@Override
	public LocationId nextLocationId() {
		return this.identifierFactory.next();
	}

	@Override
	public LocalDateTime now() {
		return LocalDateTime.now();
	}
}
