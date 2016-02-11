package org.garred.brewtour.view;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.garred.brewtour.application.event.location.BeerAddedEvent;
import org.garred.brewtour.application.event.location.BeerAvailableEvent;
import org.garred.brewtour.application.event.location.BeerModifiedEvent;
import org.garred.brewtour.application.event.location.BeerUnavailableEvent;
import org.garred.brewtour.application.event.location.LocationAddedEvent;
import org.garred.brewtour.application.event.location.LocationAddressUpdatedEvent;
import org.garred.brewtour.application.event.location.LocationDescriptionUpdatedEvent;
import org.garred.brewtour.application.event.location.LocationHoursOfOperationUpdatedEvent;
import org.garred.brewtour.application.event.location.LocationImagesUpdatedEvent;
import org.garred.brewtour.application.event.location.LocationPhoneUpdatedEvent;
import org.garred.brewtour.application.event.location.LocationPositionUpdatedEvent;
import org.garred.brewtour.application.event.location.LocationWebsiteUpdatedEvent;
import org.garred.brewtour.application.event.location.user_fired.AbstractBeerReviewAddedEvent;
import org.garred.brewtour.application.event.location.user_fired.AbstractLocationReviewAddedEvent;
import org.garred.brewtour.application.event.location.user_fired.BeerRatingUpdatedEvent;
import org.garred.brewtour.application.event.location.user_fired.LocationRatingUpdatedEvent;
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
    	final BeerView beer = BeerView.newBeerView(event.name, event.style, event.category, event.abv, event.ibu, true);
    	update(event.locationId, l -> l.beers.add(beer));
    }

	@EventHandler
    public void on(BeerModifiedEvent event) {
		update(event.locationId, l -> {
			final BeerView beer = l.requireBeer(event.name);
			beer.style = event.style;
			beer.category = event.category;
			beer.abv = event.abv;
			beer.ibu = event.ibu;
		});
	}
    @EventHandler
    public void on(BeerAvailableEvent event) {
    	update(event.locationId, l -> {
    		final BeerView beer = l.requireBeer(event.name);
    		beer.available = true;
    	});
    }
    @EventHandler
    public void on(BeerUnavailableEvent event) {
    	update(event.locationId, l -> {
    		final BeerView beer = l.requireBeer(event.name);
    		beer.available = false;
    	});
    }

    @EventHandler
    public void on(LocationRatingUpdatedEvent event) {
    	update(event.locationId, l -> l.medal = event.medal.name());
    }
    @EventHandler
    public void on(BeerRatingUpdatedEvent event) {
    	update(event.locationId, l -> l.requireBeer(event.beerName).medal = event.medal.name());
    }
    @EventHandler
    public void on(AbstractLocationReviewAddedEvent event) {
    	final Review review = new Review(event.medal.name(), event.review);
    	update(event.locationId, l -> l.reviews.add(review));
    }
    @EventHandler
    public void on(AbstractBeerReviewAddedEvent event) {
    	final Review review = new Review(event.medal.name(), event.review);
    	update(event.locationId, l -> {
    		final BeerView beer = l.requireBeer(event.name);
    		beer.addReview(review);
    	});
    }
}
