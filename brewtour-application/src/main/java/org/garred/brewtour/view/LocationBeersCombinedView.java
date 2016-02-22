package org.garred.brewtour.view;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.garred.brewtour.domain.BeerId;

public class LocationBeersCombinedView {

	public final LocationView location;
	public final Map<BeerId,BeerView> beers;

	public LocationBeersCombinedView(LocationView location, Collection<BeerView> beers) {
		this.location = location;
		this.beers = new HashMap<>();
		for(final BeerView beer : beers) {
			this.beers.put(beer.id, beer);
		}
	}

}
