package org.garred.brewtour.service;

import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.repository.LocationViewRepository;
import org.garred.brewtour.view.LocationView;

public class LocationViewRepositoryStub extends AbstractRepositoryStub<LocationId, LocationView> implements LocationViewRepository {

	@Override
	protected Class<LocationView> objectClass() {
		return LocationView.class;
	}

}
