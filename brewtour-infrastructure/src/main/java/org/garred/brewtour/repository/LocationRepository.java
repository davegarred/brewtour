package org.garred.brewtour.repository;

import java.util.Collection;

import org.garred.brewtour.application.Location;
import org.garred.brewtour.application.LocationId;

public interface LocationRepository extends Repository<LocationId, Location> {

	Collection<Location> findAll();

}
