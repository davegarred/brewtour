package org.garred.brewtour.application;

import static java.math.RoundingMode.HALF_UP;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.garred.brewtour.application.command.location.AddBeerCommand;
import org.garred.brewtour.application.command.location.AddBeerReviewCommand;
import org.garred.brewtour.application.command.location.AddLocationCommand;
import org.garred.brewtour.application.command.location.AddLocationReviewCommand;
import org.garred.brewtour.application.command.location.AddPopulatedLocationCommand;
import org.garred.brewtour.application.command.location.BeerAvailableCommand;
import org.garred.brewtour.application.command.location.BeerUnavailableCommand;
import org.garred.brewtour.application.command.location.ModifyBeerCommand;
import org.garred.brewtour.application.command.location.UpdateLocationAddressCommand;
import org.garred.brewtour.application.command.location.UpdateLocationDescriptionCommand;
import org.garred.brewtour.application.command.location.UpdateLocationHoursOfOperationCommand;
import org.garred.brewtour.application.command.location.UpdateLocationImagesCommand;
import org.garred.brewtour.application.command.location.UpdateLocationPhoneCommand;
import org.garred.brewtour.application.command.location.UpdateLocationPositionCommand;
import org.garred.brewtour.application.command.location.UpdateLocationWebsiteCommand;
import org.garred.brewtour.application.event.location.AbstractLocationAddedEvent;
import org.garred.brewtour.application.event.location.BeerAddedEvent;
import org.garred.brewtour.application.event.location.BeerAvailableEvent;
import org.garred.brewtour.application.event.location.BeerModifiedEvent;
import org.garred.brewtour.application.event.location.BeerRatingUpdatedEvent;
import org.garred.brewtour.application.event.location.BeerReviewAddedEvent;
import org.garred.brewtour.application.event.location.BeerUnavailableEvent;
import org.garred.brewtour.application.event.location.LocationAddedEvent;
import org.garred.brewtour.application.event.location.LocationAddressUpdatedEvent;
import org.garred.brewtour.application.event.location.LocationDescriptionUpdatedEvent;
import org.garred.brewtour.application.event.location.LocationHoursOfOperationUpdatedEvent;
import org.garred.brewtour.application.event.location.LocationImagesUpdatedEvent;
import org.garred.brewtour.application.event.location.LocationPhoneUpdatedEvent;
import org.garred.brewtour.application.event.location.LocationPositionUpdatedEvent;
import org.garred.brewtour.application.event.location.LocationRatingUpdatedEvent;
import org.garred.brewtour.application.event.location.LocationReviewAddedEvent;
import org.garred.brewtour.application.event.location.LocationWebsiteUpdatedEvent;
import org.garred.brewtour.application.event.location.PopulatedLocationAddedEvent;
import org.garred.brewtour.domain.AvailableImages;
import org.garred.brewtour.domain.Beer;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.Review;

@SuppressWarnings("serial")
public class Location extends AbstractAnnotatedAggregateRoot<LocationId> {

    @AggregateIdentifier
    private LocationId id;

	private String brewDbId;
	private String name;
	private String description;
	private String streetAddress;
    private String streetAddress2;
    private String city;
    private String state;
    private String postalCode;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private String hoursOfOperation;
	private String phoneNumber;
	private String website;
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

    public void updateAddress(UpdateLocationAddressCommand command) {
    	if(Objects.equals(this.streetAddress,command.streetAddress)
    			&& Objects.equals(this.streetAddress2,command.streetAddress2)
    			&& Objects.equals(this.city,command.city)
    			&& Objects.equals(this.state,command.state)
    			&& Objects.equals(this.postalCode,command.postalCode)
    			) {
    		return;
    	}
    	apply(LocationAddressUpdatedEvent.fromCommand(command));
	}

	public void updateDescription(UpdateLocationDescriptionCommand command) {
		if(!Objects.equals(this.description,command.description)) {
			apply(new LocationDescriptionUpdatedEvent(this.id, command.description));
		}
	}

	public void updateHoursOfOperation(UpdateLocationHoursOfOperationCommand command) {
		if(!Objects.equals(this.hoursOfOperation,command.hoursOfOperation)) {
			apply(new LocationHoursOfOperationUpdatedEvent(this.id, command.hoursOfOperation));
		}
	}

	public void updateImages(UpdateLocationImagesCommand command) {
		if(!Objects.equals(this.images,command.images)) {
			apply(new LocationImagesUpdatedEvent(this.id, command.images));
		}
	}

	public void updatePhoneNumber(UpdateLocationPhoneCommand command) {
		if(!Objects.equals(this.phoneNumber,command.phoneNumber)) {
			apply(new LocationPhoneUpdatedEvent(this.id, command.phoneNumber));
		}
	}

	public void updatePosition(UpdateLocationPositionCommand command) {
		if(Objects.equals(this.latitude,command.latitude)
				&& Objects.equals(this.longitude,command.longitude)) {
			return;
		}
		apply(new LocationPositionUpdatedEvent(this.id, command.latitude, command.longitude));
	}

	public void updateWebsite(UpdateLocationWebsiteCommand command) {
		if(!Objects.equals(this.website,command.website)) {
			apply(new LocationWebsiteUpdatedEvent(this.id, command.website));
		}
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
    public void on(LocationAddressUpdatedEvent event) {
    	this.streetAddress = event.streetAddress;
    	this.streetAddress2 = event.streetAddress2;
    	this.city = event.city;
    	this.state = event.state;
    	this.postalCode = event.postalCode;
	}
    @EventHandler
    public void on(LocationDescriptionUpdatedEvent event) {
    	this.description = event.description;
    }
    @EventHandler
    public void on(LocationHoursOfOperationUpdatedEvent event) {
    	this.hoursOfOperation = event.hoursOfOperation;
    }
    @EventHandler
    public void on(LocationImagesUpdatedEvent event) {
    	this.images = event.images;
    }
    @EventHandler
    public void on(LocationPhoneUpdatedEvent event) {
    	this.phoneNumber = event.phoneNumber;
    }
    @EventHandler
    public void on(LocationPositionUpdatedEvent event) {
    	this.latitude = event.latitude;
    	this.longitude = event.longitude;
    }
    @EventHandler
    public void on(LocationWebsiteUpdatedEvent event) {
    	this.website = event.website;
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
