package org.garred.brewtour.repository;

import java.util.List;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.view.BeerView;

public interface BeerViewRepository extends ViewRepository<BeerId, BeerView> {

	List<BeerView> getAll(List<BeerId> beers);

}
