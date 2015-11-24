package org.garred.brewtour.service;

import org.garred.brewtour.api.AddBeer;
import org.garred.brewtour.api.BeerAvailable;
import org.garred.brewtour.api.BeerUnavailable;
import org.garred.brewtour.api.ModifyBeer;
import org.garred.brewtour.api.ModifyLocationDescription;
import org.garred.brewtour.application.Beer;
import org.garred.brewtour.application.Location;
import org.garred.brewtour.repository.LocationRepository;

public class LocationServiceImpl implements LocationService {

	private final LocationRepository locationRepository;

	public LocationServiceImpl(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}

	@Override
	public void addBeer(AddBeer addBeer) {
		final Location location = this.locationRepository.require(addBeer.locationId);
		if(findBeer(location,addBeer.name) != null) {
			//TODO translate this exception back to the user
			throw new RuntimeException("Can not add a beer with the same name");
		}
		final Beer beer = new Beer(null, addBeer.name, null, addBeer.style, addBeer.category, addBeer.abv, addBeer.ibu, true);
		location.beers.add(beer);
		this.locationRepository.update(location);
	}
	@Override
	public void modifyBeer(ModifyBeer modifyBeer) {
		final Location location = this.locationRepository.require(modifyBeer.locationId);
		final Beer beer = findBeer(location,modifyBeer.name);
		beer.setStyle(modifyBeer.style);
		beer.setCategory(modifyBeer.category);
		beer.setAbv(modifyBeer.abv);
		beer.setIbu(modifyBeer.ibu);
		this.locationRepository.update(location);
	}

	@Override
	public void beerAvailable(BeerAvailable beerAvailable) {
		final Location location = this.locationRepository.require(beerAvailable.locationId);
		final Beer beer = findBeer(location,beerAvailable.name);
		beer.setAvailable(true);
		this.locationRepository.update(location);
	}

	@Override
	public void beerUnavailable(BeerUnavailable beerUnavailable) {
		final Location location = this.locationRepository.require(beerUnavailable.locationId);
		final Beer beer = findBeer(location,beerUnavailable.name);
		beer.setAvailable(false);
		this.locationRepository.update(location);
	}

	@Override
	public void modifyLocationDescription(ModifyLocationDescription modifyDescription) {
		final Location location = this.locationRepository.require(modifyDescription.locationId);
		location.description = modifyDescription.description;
		this.locationRepository.update(location);
	}

	private static Beer findBeer(Location location, String beername) {
		for(final Beer beer : location.beers) {
			if(beer.getName().equals(beername)) {
				return beer;
			}
		}
		return null;
	}

}
