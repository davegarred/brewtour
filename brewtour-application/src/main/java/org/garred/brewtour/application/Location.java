package org.garred.brewtour.application;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.garred.brewtour.application.command.location.AddBeerCommand;
import org.garred.brewtour.application.command.location.AddBeerRatingCommand;
import org.garred.brewtour.application.command.location.AddBeerReviewCommand;
import org.garred.brewtour.application.command.location.AddLocationCommand;
import org.garred.brewtour.application.command.location.AddLocationCommentCommand;
import org.garred.brewtour.application.command.location.AddLocationRatingCommand;
import org.garred.brewtour.application.command.location.AddLocationReviewCommand;
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
import org.garred.brewtour.application.event.location.user_fired.AbstractBeerStarRatingAddedEvent;
import org.garred.brewtour.application.event.location.user_fired.AbstractLocationReviewAddedEvent;
import org.garred.brewtour.application.event.location.user_fired.AbstractLocationStarRatingAddedEvent;
import org.garred.brewtour.application.event.location.user_fired.BeerRatingUpdatedEvent;
import org.garred.brewtour.application.event.location.user_fired.BeerReviewAddedByAnonymousEvent;
import org.garred.brewtour.application.event.location.user_fired.BeerReviewAddedByUserEvent;
import org.garred.brewtour.application.event.location.user_fired.BeerStarRatingAddedByAnonymousEvent;
import org.garred.brewtour.application.event.location.user_fired.BeerStarRatingAddedByUserEvent;
import org.garred.brewtour.application.event.location.user_fired.LocationCommentAddedEvent;
import org.garred.brewtour.application.event.location.user_fired.LocationRatingUpdatedEvent;
import org.garred.brewtour.application.event.location.user_fired.LocationReviewAddedByAnonymousEvent;
import org.garred.brewtour.application.event.location.user_fired.LocationReviewAddedByUserEvent;
import org.garred.brewtour.application.event.location.user_fired.LocationStarRatingAddedByAnonymousEvent;
import org.garred.brewtour.application.event.location.user_fired.LocationStarRatingAddedByUserEvent;
import org.garred.brewtour.domain.AvailableImages;
import org.garred.brewtour.domain.Beer;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.ReviewMedal;
import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.domain.UserReview;
import org.garred.brewtour.security.UserAuth;

@SuppressWarnings("serial")
public class Location extends AbstractAnnotatedAggregateRoot<LocationId> {

    @AggregateIdentifier
    private LocationId id;

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
	private final List<UserReview> reviews = new ArrayList<>();
	private final Map<UserId,ReviewMedal> userMedalRatings = new HashMap<>();

	private ReviewMedal medal;

    public Location() {}

    public static Location addLocation(LocationId locationId, AddLocationCommand command) {
    	final Location location = new Location();
    	location.apply(LocationAddedEvent.fromCommand(locationId, command));
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
		final Beer beer = findBeer(addBeer.beerName);
		if(beer != null) {
			throw new RuntimeException("Can not add a beer with the same name");
		}
		apply(BeerAddedEvent.fromCommand(addBeer));
	}

	public void modifyBeer(ModifyBeerCommand modifyBeer) {
		requireBeer(modifyBeer.beerName);
		apply(BeerModifiedEvent.fromCommand(modifyBeer));
	}

	public void beerAvailable(BeerAvailableCommand beerAvailable) {
		final Beer beer = requireBeer(beerAvailable.beerName);
		if(!beer.isAvailable()) {
			apply(BeerAvailableEvent.fromCommand(beerAvailable));
		}
	}
	public void beerUnavailable(BeerUnavailableCommand beerUnavailable) {
		final Beer beer = requireBeer(beerUnavailable.beerName);
		if(beer.isAvailable()) {
			apply(BeerUnavailableEvent.fromCommand(beerUnavailable));
		}
	}

	public void addComment(AddLocationCommentCommand command, UserAuth user, LocalDateTime time) {
		apply(LocationCommentAddedEvent.fromCommand(command, user.identifier(), user.screenName(), time));
	}

	public void addLocationStarRating(AddLocationRatingCommand locationRating, UserAuth user) {
		if(user.identified()) {
			apply(LocationStarRatingAddedByUserEvent.fromCommand(locationRating, user.identifier(), user.screenName()));
		} else {
			apply(LocationStarRatingAddedByAnonymousEvent.fromCommand(locationRating, user.identifier(), user.screenName()));
		}
		final ReviewMedal updatedMedal = ReviewMedal.average(new ArrayList<>(this.userMedalRatings.values()));
		if(!Objects.equals(updatedMedal, this.medal)) {
			apply(new LocationRatingUpdatedEvent(this.id, updatedMedal));
		}
	}
	public void addLocationReview(AddLocationReviewCommand locationReview, UserAuth user, LocalDateTime time) {
		if(user.identified()) {
			apply(LocationReviewAddedByUserEvent.fromCommand(locationReview, user.identifier(), user.screenName(), time));
		} else {
			apply(LocationReviewAddedByAnonymousEvent.fromCommand(locationReview, user.identifier(), time));
		}
		final ReviewMedal updatedMedal = ReviewMedal.average(new ArrayList<>(this.userMedalRatings.values()));
		if(!Objects.equals(updatedMedal, this.medal)) {
			apply(new LocationRatingUpdatedEvent(this.id, updatedMedal));
		}
	}

	public void addBeerStarRating(AddBeerRatingCommand beerReview, UserAuth user) {
		final Beer beer = requireBeer(beerReview.beerName);
		if(user.identified()) {
			apply(BeerStarRatingAddedByUserEvent.fromCommand(beerReview, user.identifier(), user.screenName()));
		} else {
			apply(BeerStarRatingAddedByAnonymousEvent.fromCommand(beerReview, user.identifier()));
		}
		final ReviewMedal updatedMedal = ReviewMedal.average(new ArrayList<>(beer.getUserMedalRatings().values()));
		if(!Objects.equals(updatedMedal, beer.getMedal())) {
			apply(new BeerRatingUpdatedEvent(this.id, beerReview.beerName, updatedMedal));
		}
	}
	public void addBeerReview(AddBeerReviewCommand beerReview, UserAuth user, LocalDateTime time) {
		final Beer beer = requireBeer(beerReview.beerName);
		if(user.identified()) {
			apply(BeerReviewAddedByUserEvent.fromCommand(beerReview, user.identifier(), user.screenName(), time));
		} else {
			apply(BeerReviewAddedByAnonymousEvent.fromCommand(beerReview, user.identifier(), time));
		}
		final ReviewMedal updatedMedal = ReviewMedal.average(new ArrayList<>(beer.getUserMedalRatings().values()));
		if(!Objects.equals(updatedMedal, beer.getMedal())) {
			apply(new BeerRatingUpdatedEvent(this.id, beerReview.beerName, updatedMedal));
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
    	final Beer beer = Beer.fromEvent(event.beerName, null, event.style, event.category, event.abv, event.ibu, true);
		this.beers.add(beer);
    }

    @EventHandler
    public void on(BeerModifiedEvent event) {
    	final Beer beer = findBeer(event.beerName);
    	beer.setStyle(event.style);
    	beer.setCategory(event.category);
    	beer.setAbv(event.abv);
    	beer.setIbu(event.ibu);
    }
    @EventHandler
    public void on(BeerAvailableEvent event) {
    	final Beer beer = findBeer(event.beerName);
    	beer.setAvailable(true);
    }
    @EventHandler
    public void on(BeerUnavailableEvent event) {
    	final Beer beer = findBeer(event.beerName);
    	beer.setAvailable(false);
    }
    @EventHandler
    public void on(AbstractLocationStarRatingAddedEvent event) {
    	this.userMedalRatings.put(event.userId, event.medal);
    }
    @EventHandler
    public void on(AbstractLocationReviewAddedEvent event) {
    	final UserReview review = new UserReview(event.userId, event.medal, event.time, event.review);
    	this.reviews.add(review);
    	on((AbstractLocationStarRatingAddedEvent)event);
    }
    @EventHandler
    public void on(AbstractBeerStarRatingAddedEvent event) {
    	final Beer beer = findBeer(event.beerName);
    	beer.setMedalRating(event.userId, event.medal);
    }
    @EventHandler
    public void on(AbstractBeerReviewAddedEvent event) {
    	final UserReview review = new UserReview(event.userId, event.medal, event.time, event.review);
    	final Beer beer = findBeer(event.beerName);
    	beer.addReview(review);
    	on((AbstractBeerStarRatingAddedEvent)event);
    }

}
