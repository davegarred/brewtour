package org.garred.brewtour.repository;

import java.util.Collection;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.view.LocationView;

public interface LocationViewRepository extends ViewRepository<LocationId, LocationView> {

	Collection<LocationView> findLocationsWithBeer(BeerId beerId);

}
