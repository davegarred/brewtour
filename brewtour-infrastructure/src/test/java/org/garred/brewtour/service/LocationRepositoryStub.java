package org.garred.brewtour.service;

import org.garred.brewtour.application.Location;
import org.garred.brewtour.application.LocationId;
import org.garred.brewtour.repository.LocationRepository;

public class LocationRepositoryStub extends AbstractRepositoryStub<LocationId, Location> implements LocationRepository {

}
