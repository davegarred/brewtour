package org.garred.brewtour.service;

import static org.garred.brewtour.application.LocaleId.SEATTLE;

import java.util.function.Consumer;

import org.garred.brewtour.api.AddBeer;
import org.garred.brewtour.api.BeerAvailable;
import org.garred.brewtour.api.BeerUnavailable;
import org.garred.brewtour.api.LocationCommand;
import org.garred.brewtour.api.ModifyBeer;
import org.garred.brewtour.api.ModifyLocationDescription;
import org.garred.brewtour.application.Locale;
import org.garred.brewtour.application.LocaleId;
import org.garred.brewtour.application.Location;
import org.garred.brewtour.application.LocationId;
import org.garred.brewtour.config.Secure;
import org.garred.brewtour.repository.LocaleRepository;
import org.garred.brewtour.repository.LocationRepository;

public class LocationServiceImpl implements LocationService {

	private final LocationRepository locationRepository;
	private final LocaleRepository localeRepository;

	public LocationServiceImpl(LocationRepository locationRepository, LocaleRepository localeRepository) {
		this.locationRepository = locationRepository;
		this.localeRepository = localeRepository;
	}

	@Override
	@Secure
	public void addBeer(AddBeer addBeer) {
		process(addBeer, l -> l.addBeer(addBeer));
	}
	@Override
	@Secure
	public void modifyBeer(ModifyBeer modifyBeer) {
		process(modifyBeer, l -> l.modifyBeer(modifyBeer));
	}

	@Override
	@Secure
	public void beerAvailable(BeerAvailable beerAvailable) {
		process(beerAvailable, l -> l.beerAvailable(beerAvailable));
	}

	@Override
	@Secure
	public void beerUnavailable(BeerUnavailable beerUnavailable) {
		process(beerUnavailable, l -> l.beerUnavailable(beerUnavailable));
	}

	@Override
	@Secure
	public void modifyLocationDescription(ModifyLocationDescription modifyDescription) {
		process(modifyDescription, l -> l.modifyLocationDescription(modifyDescription));
	}

	private void process(LocationCommand command, Consumer<Location> consumer) {
		final Location location = this.locationRepository.require(command.locationId);
		consumer.accept(location);
		this.locationRepository.update(location);
	}

	@Override
	public Location getLocation(LocationId locationId) {
		return this.locationRepository.get(locationId);
	}

	@Override
	public Locale getLocale(LocaleId seattle) {
		return this.localeRepository.get(SEATTLE);
	}

}
