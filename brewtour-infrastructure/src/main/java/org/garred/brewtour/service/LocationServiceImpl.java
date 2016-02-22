package org.garred.brewtour.service;

import static org.garred.brewtour.domain.LocaleId.SEATTLE;

import java.util.List;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.garred.brewtour.domain.LocaleId;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.repository.BeerViewRepository;
import org.garred.brewtour.repository.LocaleViewRepository;
import org.garred.brewtour.repository.LocationViewRepository;
import org.garred.brewtour.view.BeerView;
import org.garred.brewtour.view.LocaleView;
import org.garred.brewtour.view.LocationBeersCombinedView;
import org.garred.brewtour.view.LocationView;

public class LocationServiceImpl implements LocationService, LocationQueryService {

	private final LocationViewRepository locationRepository;
	private final BeerViewRepository beerRepository;
	private final LocaleViewRepository localeRepository;
	private final CommandGateway commandGateway;

	public LocationServiceImpl(LocationViewRepository locationRepository, BeerViewRepository beerRepository,
			LocaleViewRepository localeRepository, CommandGateway commandGateway) {
		this.locationRepository = locationRepository;
		this.beerRepository = beerRepository;
		this.localeRepository = localeRepository;
		this.commandGateway = commandGateway;
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
	public LocationBeersCombinedView getLocationDetails(LocationId locationId) {
		final LocationView location = this.locationRepository.get(locationId);
		final List<BeerView> beers = this.beerRepository.getAll(location.beers);
		return new LocationBeersCombinedView(location, beers);
	}

	@Override
	public LocaleView getLocale(LocaleId seattle) {
		return this.localeRepository.get(SEATTLE);
	}

}
