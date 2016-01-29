package org.garred.brewtour.view;

import java.util.ArrayList;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.garred.brewtour.application.event.location.BeerAddedEvent;
import org.garred.brewtour.application.event.location.BeerAvailableEvent;
import org.garred.brewtour.application.event.location.BeerModifiedEvent;
import org.garred.brewtour.application.event.location.BeerReviewAddedEvent;
import org.garred.brewtour.application.event.location.BeerUnavailableEvent;
import org.garred.brewtour.application.event.location.LocationAddedEvent;
import org.garred.brewtour.application.event.location.LocationAddressUpdatedEvent;
import org.garred.brewtour.application.event.location.LocationDescriptionUpdatedEvent;
import org.garred.brewtour.application.event.location.LocationHoursOfOperationUpdatedEvent;
import org.garred.brewtour.application.event.location.LocationImagesUpdatedEvent;
import org.garred.brewtour.application.event.location.LocationPhoneUpdatedEvent;
import org.garred.brewtour.application.event.location.LocationPositionUpdatedEvent;
import org.garred.brewtour.application.event.location.LocationReviewAddedEvent;
import org.garred.brewtour.application.event.location.LocationWebsiteUpdatedEvent;
import org.garred.brewtour.application.event.location.PopulatedLocationAddedEvent;
import org.garred.brewtour.domain.Beer;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.repository.LocationViewRepository;

public class LocationViewEventHandler extends AbstractViewEventHandler<LocationId, LocationView> {

	public LocationViewEventHandler(LocationViewRepository repository) {
		super(repository);
	}

	@EventHandler
    public void on(LocationAddedEvent event) {
		this.repository.save(LocationView.fromEvent(event));
    }
	@EventHandler
	public void on(PopulatedLocationAddedEvent event) {
		this.repository.save(LocationView.fromEvent(event));
	}

    @EventHandler
    public void on(LocationAddressUpdatedEvent event) {
    	update(event.locationId, l -> {
    		l.streetAddress = event.streetAddress;
    		l.streetAddress2 = event.streetAddress2;
    		l.city = event.city;
    		l.state = event.state;
    		l.postalCode = event.postalCode;
    	});
	}
    @EventHandler
    public void on(LocationDescriptionUpdatedEvent event) {
    	update(event.locationId, l -> l.description = event.description);
    }
    @EventHandler
    public void on(LocationHoursOfOperationUpdatedEvent event) {
    	update(event.locationId, l -> l.hoursOfOperation = event.hoursOfOperation);
    }
    @EventHandler
    public void on(LocationImagesUpdatedEvent event) {
    	update(event.locationId, l -> l.images = event.images);
    }
    @EventHandler
    public void on(LocationPhoneUpdatedEvent event) {
    	update(event.locationId, l -> l.phoneNumber = event.phoneNumber);
    }
    @EventHandler
    public void on(LocationPositionUpdatedEvent event) {
    	update(event.locationId, l -> {
    		l.latitude = event.latitude;
    		l.longitude = event.longitude;
    	});
    }
    @EventHandler
    public void on(LocationWebsiteUpdatedEvent event) {
    	update(event.locationId, l -> l.website = event.website);
    }

    @EventHandler
    public void on(BeerAddedEvent event) {
    	final Beer beer = new Beer(null, event.name, null, event.style, event.category, event.abv, event.ibu, true, new ArrayList<>());
    	update(event.locationId, l -> l.beers.add(beer));
    }

	@EventHandler
    public void on(BeerModifiedEvent event) {
		update(event.locationId, l -> {
			final Beer beer = l.requireBeer(event.name);
			beer.setStyle(event.style);
			beer.setCategory(event.category);
			beer.setAbv(event.abv);
			beer.setIbu(event.ibu);
		});
	}
    @EventHandler
    public void on(BeerAvailableEvent event) {
    	update(event.locationId, l -> {
    		final Beer beer = l.requireBeer(event.name);
    		beer.setAvailable(true);
    	});
    }
    @EventHandler
    public void on(BeerUnavailableEvent event) {
    	update(event.locationId, l -> {
    		final Beer beer = l.requireBeer(event.name);
    		beer.setAvailable(false);
    	});
    }
    @EventHandler
    public void on(LocationReviewAddedEvent event) {
    	update(event.locationId, l -> l.reviews.add(event.review));
    }
    @EventHandler
    public void on(BeerReviewAddedEvent event) {
    	update(event.locationId, l -> {
    		final Beer beer = l.requireBeer(event.name);
    		beer.addReview(event.review);
    	});
    }
}
