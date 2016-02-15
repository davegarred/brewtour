package org.garred.brewtour.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.repository.LocationViewRepository;
import org.garred.brewtour.view.LocationView;

public class LocationViewRepositoryStub extends AbstractRepositoryStub<LocationId, LocationView> implements LocationViewRepository {

	@Override
	protected Class<LocationView> objectClass() {
		return LocationView.class;
	}

	@Override
	public Collection<LocationView> findLocationsWithBeer(BeerId beerId) {
		final List<LocationView> result = new ArrayList<>();
		for(final LocationView view : this.objectMap.values()) {
			if(view.beers.containsKey(beerId)) {
				result.add(view);
			}
		}
		return result;
	}

}
