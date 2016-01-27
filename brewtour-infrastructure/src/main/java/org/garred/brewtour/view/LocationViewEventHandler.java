package org.garred.brewtour.view;

import java.util.ArrayList;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.garred.brewtour.application.Beer;
import org.garred.brewtour.application.LocationId;
import org.garred.brewtour.application.event.BeerAddedEvent;
import org.garred.brewtour.application.event.BeerAvailableEvent;
import org.garred.brewtour.application.event.BeerModifiedEvent;
import org.garred.brewtour.application.event.BeerReviewAddedEvent;
import org.garred.brewtour.application.event.BeerUnavailableEvent;
import org.garred.brewtour.application.event.LocationAddedEvent;
import org.garred.brewtour.application.event.LocationReviewAddedEvent;
import org.garred.brewtour.application.event.PopulatedLocationAddedEvent;
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
