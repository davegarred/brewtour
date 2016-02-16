package org.garred.brewtour.view;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.garred.brewtour.application.event.beer.AbstractBeerReviewAddedEvent;
import org.garred.brewtour.application.event.beer.BeerAddedEvent;
import org.garred.brewtour.application.event.beer.BeerModifiedEvent;
import org.garred.brewtour.application.event.beer.BeerRatingUpdatedEvent;
import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.repository.BeerViewRepository;

public class BeerViewEventHandler extends AbstractViewEventHandler<BeerId, BeerView> {

	public BeerViewEventHandler(BeerViewRepository repository) {
		super(repository);
	}

	@EventHandler
    public void on(BeerAddedEvent event) {
		this.repository.save(BeerView.newBeerView(event.beerId, event.beerName, event.style, event.category, event.abv, event.ibu));
    }


	@EventHandler
    public void on(BeerModifiedEvent event) {
		update(event.beerId, b -> {
			b.style = event.style;
			b.category = event.category;
			b.abv = event.abv;
			b.ibu = event.ibu;
		});
	}

    @EventHandler
    public void on(BeerRatingUpdatedEvent event) {
    	update(event.beerId, b -> b.medal = event.medal.toString());
    }
    @EventHandler
    public void on(AbstractBeerReviewAddedEvent event) {
    	update(event.beerId, b -> b.addReview(new Review(event.userScreenName, event.medal.name(), event.review)));
    }
}
