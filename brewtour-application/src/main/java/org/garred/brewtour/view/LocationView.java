package org.garred.brewtour.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.garred.brewtour.application.event.location.AbstractLocationAddedEvent;
import org.garred.brewtour.application.event.location.LocationAddedEvent;
import org.garred.brewtour.domain.AvailableImages;
import org.garred.brewtour.domain.Beer;
import org.garred.brewtour.domain.Entity;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.UserReview;

public class LocationView extends AbstractView implements Entity<LocationId> {

	public LocationId locationId;
	public String name;
	public String description;
	public String streetAddress;
	public String streetAddress2;
    public String city;
    public String state;
    public String postalCode;
	public BigDecimal latitude;
	public BigDecimal longitude;
	public String hoursOfOperation;
	public String phoneNumber;
	public String website;
	public AvailableImages images;
	public List<Beer> beers;
	public List<UserReview> reviews;

	@Override
	public LocationId identifier() {
		return this.locationId;
	}

	private static LocationView initialize(AbstractLocationAddedEvent event) {
		final LocationView view = new LocationView();
		view.locationId = event.locationId;
		view.beers = new ArrayList<>();
		view.reviews = new ArrayList<>();
		return view;
	}
	public static LocationView fromEvent(LocationAddedEvent event) {
		return initialize(event);
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
