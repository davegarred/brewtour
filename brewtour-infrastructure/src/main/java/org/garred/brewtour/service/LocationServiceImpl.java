package org.garred.brewtour.service;

import static org.garred.brewtour.application.LocaleId.SEATTLE;

import org.garred.brewtour.api.AddBeer;
import org.garred.brewtour.api.BeerAvailable;
import org.garred.brewtour.api.BeerUnavailable;
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
//		validateAdminOrTest();
		final Location location = this.locationRepository.require(addBeer.locationId);
		location.addBeer(addBeer);
		this.locationRepository.update(location);
	}
	@Override
	@Secure
	public void modifyBeer(ModifyBeer modifyBeer) {
//		validateAdminOrTest();
		final Location location = this.locationRepository.require(modifyBeer.locationId);
		location.modifyBeer(modifyBeer);
		this.locationRepository.update(location);
	}

	@Override
	@Secure
	public void beerAvailable(BeerAvailable beerAvailable) {
//		validateAdminOrTest();
		final Location location = this.locationRepository.require(beerAvailable.locationId);
		location.beerAvailable(beerAvailable);
		this.locationRepository.update(location);
	}

	@Override
	@Secure
	public void beerUnavailable(BeerUnavailable beerUnavailable) {
//		validateAdminOrTest();
		final Location location = this.locationRepository.require(beerUnavailable.locationId);
		location.beerUnavailable(beerUnavailable);
		this.locationRepository.update(location);
	}

	@Override
	@Secure
	public void modifyLocationDescription(ModifyLocationDescription modifyDescription) {
//		validateAdminOrTest();
		final Location location = this.locationRepository.require(modifyDescription.locationId);
		location.modifyLocationDescription(modifyDescription);
		this.locationRepository.update(location);
	}

//	//TODO use aspect
//	private void validateAdminOrTest() {
//		final UserDetails user = this.userRepo.get(UserHandler.get());
//		if(user == null || !(user.isAdmin() || user.isTestUser())) {
//			throw new RuntimeException("Attempt to modify an object without permission");
//		}
//	}

	@Override
	public Location getLocation(LocationId locationId) {
		return this.locationRepository.get(locationId);
	}

	@Override
	public Locale getLocale(LocaleId seattle) {
		return this.localeRepository.get(SEATTLE);
	}

}
