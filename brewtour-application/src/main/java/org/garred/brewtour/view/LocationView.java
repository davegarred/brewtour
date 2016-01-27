package org.garred.brewtour.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.garred.brewtour.application.AvailableImages;
import org.garred.brewtour.application.Beer;
import org.garred.brewtour.application.Entity;
import org.garred.brewtour.application.LocationId;
import org.garred.brewtour.application.Review;
import org.garred.brewtour.application.event.AbstractLocationAddedEvent;
import org.garred.brewtour.application.event.LocationAddedEvent;
import org.garred.brewtour.application.event.PopulatedLocationAddedEvent;

public class LocationView implements Entity<LocationId> {

	public LocationId locationId;
	public String brewDbId;
	public String name;
	public String description;
	public BigDecimal latitude;
	public BigDecimal longitude;
	public AvailableImages images;
	public List<Beer> beers;
	public List<Review> reviews;

	@Override
	public LocationId identifier() {
		return this.locationId;
	}

	private static LocationView initialize(AbstractLocationAddedEvent event) {
		final LocationView view = new LocationView();
		view.locationId = event.locationId;
		view.brewDbId = event.brewDbId;
		view.name = event.name;
		view.description = event.description;
		view.latitude = event.latitude;
		view.longitude = event.longitude;
		view.images = event.images;
		view.beers = new ArrayList<>();
		view.reviews = new ArrayList<>();
		return view;
	}
	public static LocationView fromEvent(LocationAddedEvent event) {
		return initialize(event);
	}
	public static LocationView fromEvent(PopulatedLocationAddedEvent event) {
		final LocationView view = initialize(event);
		view.beers = event.beers;
		return view;
	}

	public Beer findBeer(String beername) {
		for(final Beer beer : this.beers) {
			if(beer.getName().equals(beername)) {
				return beer;
			}
		}
		return null;
	}
	public Beer requireBeer(String beerName) {
		final Beer beer = findBeer(beerName);
		if(beer == null) {
			throw new RuntimeException("Beer '" + beerName + "' could not be found in location view " + this.locationId);
		}
		return beer;
	}

}
