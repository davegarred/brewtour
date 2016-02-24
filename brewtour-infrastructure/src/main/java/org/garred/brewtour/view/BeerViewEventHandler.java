package org.garred.brewtour.view;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.garred.brewtour.application.event.beer.AbstractBeerReviewAddedEvent;
import org.garred.brewtour.application.event.beer.BeerAbvUpdatedEvent;
import org.garred.brewtour.application.event.beer.BeerAddedEvent;
import org.garred.brewtour.application.event.beer.BeerDescriptionUpdatedEvent;
import org.garred.brewtour.application.event.beer.BeerIbuUpdatedEvent;
import org.garred.brewtour.application.event.beer.BeerImagesUpdatedEvent;
import org.garred.brewtour.application.event.beer.BeerRatingUpdatedEvent;
import org.garred.brewtour.application.event.beer.BeerSrmUpdatedEvent;
import org.garred.brewtour.application.event.beer.BeerStyleUpdatedEvent;
import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.repository.BeerViewRepository;

public class BeerViewEventHandler extends AbstractViewEventHandler<BeerId, BeerView> {

	public BeerViewEventHandler(BeerViewRepository repository) {
		super(repository);
	}

	@EventHandler
    public void on(BeerAddedEvent event) {
		this.repository.save(BeerView.newBeerView(event.beerId, event.beerName, event.breweryName));
    }


	@EventHandler
    public void on(BeerDescriptionUpdatedEvent event) {
		update(event.beerId, b -> b.description = event.description);
	}
	@EventHandler
	public void on(BeerStyleUpdatedEvent event) {
		update(event.beerId, b -> b.style = event.style);
	}
	@EventHandler
	public void on(BeerAbvUpdatedEvent event) {
		update(event.beerId, b -> b.abv = event.abv);
	}
	@EventHandler
	public void on(BeerIbuUpdatedEvent event) {
		update(event.beerId, b -> b.ibu = event.ibu);
	}
	@EventHandler
	public void on(BeerSrmUpdatedEvent event) {
		update(event.beerId, b -> b.srm = event.srm);
	}

	@EventHandler
	public void on(BeerImagesUpdatedEvent event) {
		update(event.beerId, b -> b.images = event.images);
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
