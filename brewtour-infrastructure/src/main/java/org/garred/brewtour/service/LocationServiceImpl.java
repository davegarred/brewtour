package org.garred.brewtour.service;

import static org.garred.brewtour.domain.LocaleId.SEATTLE;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.garred.brewtour.domain.LocaleId;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.repository.LocaleViewRepository;
import org.garred.brewtour.repository.LocationViewRepository;
import org.garred.brewtour.security.Secure;
import org.garred.brewtour.view.LocaleView;
import org.garred.brewtour.view.LocationView;

public class LocationServiceImpl implements LocationService, LocationQueryService {

	private final LocationViewRepository locationRepository;
	private final LocaleViewRepository localeRepository;
	private final CommandGateway commandGateway;

	public LocationServiceImpl(LocationViewRepository locationRepository, LocaleViewRepository localeRepository, CommandGateway commandGateway) {
		this.locationRepository = locationRepository;
		this.localeRepository = localeRepository;
		this.commandGateway = commandGateway;
	}

	@Override
	@Secure
	public void fireSecuredCommand(Object command) {
		this.commandGateway.sendAndWait(command);
	}

	@Override
	public void fireCommand(Object command) {
		this.commandGateway.sendAndWait(command);
	}

	@Override
	public LocationView getLocation(LocationId locationId) {
		return this.locationRepository.get(locationId);
	}

	@Override
	public LocaleView getLocale(LocaleId seattle) {
		return this.localeRepository.get(SEATTLE);
	}

}
