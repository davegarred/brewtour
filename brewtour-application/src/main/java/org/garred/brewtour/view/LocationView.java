package org.garred.brewtour.view;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.garred.brewtour.application.event.location.AbstractLocationAddedEvent;
import org.garred.brewtour.application.event.location.LocationAddedEvent;
import org.garred.brewtour.domain.AvailableImages;
import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.BreweryId;
import org.garred.brewtour.domain.Entity;
import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = NON_NULL)
public class LocationView extends AbstractView implements Entity<LocationId> {

	public LocationId locationId;
	public String name;
	public String description;
	public BreweryId breweryId;
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
	public List<BeerId> beers;
	public List<Review> reviews;

	public String medal;

	@Override
	public LocationId identifier() {
		return this.locationId;
	}

	private static LocationView initialize(AbstractLocationAddedEvent event) {
		final LocationView view = new LocationView();
		view.locationId = event.locationId;
		view.name = event.name;
		view.beers = new ArrayList<>();
		view.reviews = new ArrayList<>();
		return view;
	}
	public static LocationView fromEvent(LocationAddedEvent event) {
		return initialize(event);
	}

}
