package org.garred.brewtour.application.cqrs;

import static java.math.RoundingMode.HALF_UP;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.garred.brewtour.api.AddBeerCommand;
import org.garred.brewtour.api.AddBeerReviewCommand;
import org.garred.brewtour.api.AddLocationCommand;
import org.garred.brewtour.api.AddLocationReviewCommand;
import org.garred.brewtour.api.AddPopulatedLocationCommand;
import org.garred.brewtour.api.BeerAvailableCommand;
import org.garred.brewtour.api.BeerUnavailableCommand;
import org.garred.brewtour.api.ModifyBeerCommand;
import org.garred.brewtour.application.AvailableImages;
import org.garred.brewtour.application.Beer;
import org.garred.brewtour.application.LocationId;
import org.garred.brewtour.application.Review;
import org.garred.brewtour.application.event.AbstractLocationAddedEvent;
import org.garred.brewtour.application.event.BeerAddedEvent;
import org.garred.brewtour.application.event.BeerAvailableEvent;
import org.garred.brewtour.application.event.BeerModifiedEvent;
import org.garred.brewtour.application.event.BeerRatingUpdatedEvent;
import org.garred.brewtour.application.event.BeerReviewAddedEvent;
import org.garred.brewtour.application.event.BeerUnavailableEvent;
import org.garred.brewtour.application.event.LocationAddedEvent;
import org.garred.brewtour.application.event.LocationRatingUpdatedEvent;
import org.garred.brewtour.application.event.LocationReviewAddedEvent;
import org.garred.brewtour.application.event.PopulatedLocationAddedEvent;

@SuppressWarnings("serial")
public class Location extends AbstractAnnotatedAggregateRoot<LocationId> {

    @AggregateIdentifier
    private LocationId id;

	private String brewDbId;
	private String name;
	private String description;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private AvailableImages images;
	private final List<Beer> beers = new ArrayList<>();
	private final List<Review> reviews = new ArrayList<>();

	private BigDecimal averageStars;

    public Location() {}

    public static Location addLocation(LocationId locationId, AddLocationCommand command) {
    	final Location location = new Location();
    	location.apply(LocationAddedEvent.fromCommand(locationId, command));
    	return location;
    }
    public static Location addPopulatedLocation(LocationId locationId, AddPopulatedLocationCommand command) {
    	final Location location = new Location();
    	location.apply(PopulatedLocationAddedEvent.fromCommand(locationId, command));
    	return location;
    }

	public void addBeer(AddBeerCommand addBeer) {
		final Beer beer = findBeer(addBeer.name);
		if(beer != null) {
			throw new RuntimeException("Can not add a beer with the same name");
		}
		apply(BeerAddedEvent.fromCommand(addBeer));
	}

	public void modifyBeer(ModifyBeerCommand modifyBeer) {
		requireBeer(modifyBeer.name);
		apply(BeerModifiedEvent.fromCommand(modifyBeer));
	}

	public void beerAvailable(BeerAvailableCommand beerAvailable) {
		final Beer beer = requireBeer(beerAvailable.name);
		if(!beer.isAvailable()) {
			apply(BeerAvailableEvent.fromCommand(beerAvailable));
		}
	}
	public void beerUnavailable(BeerUnavailableCommand beerUnavailable) {
		final Beer beer = requireBeer(beerUnavailable.name);
		if(beer.isAvailable()) {
			apply(BeerUnavailableEvent.fromCommand(beerUnavailable));
		}
	}

	public void addLocationReview(AddLocationReviewCommand locationReview) {
		apply(LocationReviewAddedEvent.fromCommand(locationReview));
		final BigDecimal avg = updateReviewAverage();
		if(avg != null && !Objects.equals(avg, this.averageStars)) {
			apply(new LocationRatingUpdatedEvent(this.id, avg));
		}
	}

	public void addBeerReview(AddBeerReviewCommand beerReview) {
		final Beer beer = requireBeer(beerReview.name);
		apply(BeerReviewAddedEvent.fromCommand(beerReview));
		final BigDecimal avg = beer.updateReviewAverage();
		if(avg != null && !Objects.equals(avg, beer.getAverageStars())) {
			apply(new BeerRatingUpdatedEvent(this.id, beerReview.name, avg));
		}
	}

	private Beer findBeer(String beername) {
		for(final Beer beer : this.beers) {
			if(beer.getName().equals(beername)) {
				return beer;
			}
		}
		return null;
	}
	private Beer requireBeer(String beerName) {
		final Beer beer = findBeer(beerName);
		if(beer == null) {
			throw new RuntimeException("Beer '" + beerName + "' could not be found");
		}
		return beer;
	}
	private BigDecimal updateReviewAverage() {
		int count = 0;
		int totalStars = 0;
		for(final Review review : this.reviews) {
			count ++;
			totalStars += review.stars;
		}
		if(count > 0) {
			return new BigDecimal(((double)totalStars) / (double)count).setScale(1, HALF_UP);
		}
		return null;
	}


    @EventHandler
    public void on(AbstractLocationAddedEvent event) {
    	this.id = event.locationId;
    }
    @EventHandler
    public void on(BeerAddedEvent event) {
    	final Beer beer = new Beer(null, event.name, null, event.style, event.category, event.abv, event.ibu, true, new ArrayList<>());
		this.beers.add(beer);
    }
    @EventHandler
    public void on(BeerModifiedEvent event) {
		final Beer beer = findBeer(event.name);
		beer.setStyle(event.style);
		beer.setCategory(event.category);
		beer.setAbv(event.abv);
		beer.setIbu(event.ibu);
	}
    @EventHandler
    public void on(BeerAvailableEvent event) {
    	final Beer beer = findBeer(event.name);
    	beer.setAvailable(true);
    }
    @EventHandler
    public void on(BeerUnavailableEvent event) {
    	final Beer beer = findBeer(event.name);
    	beer.setAvailable(false);
    }
    @EventHandler
    public void on(LocationReviewAddedEvent event) {
    	this.reviews.add(event.review);
    }
    @EventHandler
    public void on(BeerReviewAddedEvent event) {
    	final Beer beer = findBeer(event.name);
    	beer.addReview(event.review);
    }

}
