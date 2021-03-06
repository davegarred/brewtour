package org.garred.brewtour.domain.aggregates;

import static java.util.Collections.emptyList;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.garred.brewtour.domain.AvailableImages;
import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.ReviewMedal;
import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.domain.UserReview;
import org.garred.brewtour.domain.command.location.AddLocationCommand;
import org.garred.brewtour.domain.command.location.AddLocationCommentCommand;
import org.garred.brewtour.domain.command.location.AddLocationRatingCommand;
import org.garred.brewtour.domain.command.location.AddLocationReviewCommand;
import org.garred.brewtour.domain.command.location.BeerAvailableCommand;
import org.garred.brewtour.domain.command.location.BeerUnavailableCommand;
import org.garred.brewtour.domain.command.location.UpdateLocationAddressCommand;
import org.garred.brewtour.domain.command.location.UpdateLocationDescriptionCommand;
import org.garred.brewtour.domain.command.location.UpdateLocationHoursOfOperationCommand;
import org.garred.brewtour.domain.command.location.UpdateLocationImagesCommand;
import org.garred.brewtour.domain.command.location.UpdateLocationPhoneCommand;
import org.garred.brewtour.domain.command.location.UpdateLocationPositionCommand;
import org.garred.brewtour.domain.command.location.UpdateLocationWebsiteCommand;
import org.garred.brewtour.domain.event.location.AbstractLocationAddedEvent;
import org.garred.brewtour.domain.event.location.BeerAvailableEvent;
import org.garred.brewtour.domain.event.location.BeerUnavailableEvent;
import org.garred.brewtour.domain.event.location.LocationAddedEvent;
import org.garred.brewtour.domain.event.location.LocationAddressUpdatedEvent;
import org.garred.brewtour.domain.event.location.LocationBreweryAssociationUpdatedEvent;
import org.garred.brewtour.domain.event.location.LocationDescriptionUpdatedEvent;
import org.garred.brewtour.domain.event.location.LocationHoursOfOperationUpdatedEvent;
import org.garred.brewtour.domain.event.location.LocationImagesUpdatedEvent;
import org.garred.brewtour.domain.event.location.LocationPhoneUpdatedEvent;
import org.garred.brewtour.domain.event.location.LocationPositionUpdatedEvent;
import org.garred.brewtour.domain.event.location.LocationWebsiteUpdatedEvent;
import org.garred.brewtour.domain.event.location.user_fired.AbstractLocationReviewAddedEvent;
import org.garred.brewtour.domain.event.location.user_fired.AbstractLocationStarRatingAddedEvent;
import org.garred.brewtour.domain.event.location.user_fired.LocationCommentAddedEvent;
import org.garred.brewtour.domain.event.location.user_fired.LocationRatingUpdatedEvent;
import org.garred.brewtour.domain.event.location.user_fired.LocationReviewAddedByAnonymousEvent;
import org.garred.brewtour.domain.event.location.user_fired.LocationReviewAddedByUserEvent;
import org.garred.brewtour.domain.event.location.user_fired.LocationStarRatingAddedByAnonymousEvent;
import org.garred.brewtour.domain.event.location.user_fired.LocationStarRatingAddedByUserEvent;
import org.garred.brewtour.security.UserAuth;

@SuppressWarnings("serial")
public class Location extends AbstractAnnotatedAggregateRoot<LocationId> {

    @AggregateIdentifier
    private LocationId id;

	private String name;
	private LocationId associatedBreweryId;
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
	private final Set<BeerId> availableBeers = new HashSet<>();
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

	public void associateWithBrewery(LocationId associatedBrewery) {
		if(!Objects.equals(this.associatedBreweryId, associatedBrewery)) {
			apply(new LocationBreweryAssociationUpdatedEvent(this.id, associatedBrewery));
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

	public void beerAvailable(BeerAvailableCommand command) {
		if(!this.availableBeers.contains(command.beerId)) {
			apply(new BeerAvailableEvent(this.id, command.beerId));
		}
	}
	public void beerUnavailable(BeerUnavailableCommand command) {
		if(this.availableBeers.contains(command.beerId)) {
			apply(new BeerUnavailableEvent(this.id, command.beerId));
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
		updateRatings();
	}

	public void addLocationReview(AddLocationReviewCommand locationReview, UserAuth user, LocalDateTime time) {
		if(user.identified()) {
			apply(LocationReviewAddedByUserEvent.fromCommand(locationReview, user.identifier(), user.screenName(), time));
		} else {
			apply(LocationReviewAddedByAnonymousEvent.fromCommand(locationReview, user.identifier(), time));
		}
		updateRatings();
	}

	private void updateRatings() {
		final ReviewMedal updatedMedal = ReviewMedal.average(this.userMedalRatings.values(), emptyList());
		if(!Objects.equals(updatedMedal, this.medal)) {
			apply(new LocationRatingUpdatedEvent(this.id, updatedMedal));
		}
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
    public void on(LocationBreweryAssociationUpdatedEvent event) {
    	this.associatedBreweryId = event.associatedBreweryId;
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
    public void on(BeerAvailableEvent event) {
    	this.availableBeers.add(event.beerId);
    }
    @EventHandler
    public void on(BeerUnavailableEvent event) {
    	this.availableBeers.remove(event.beerId);
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

}
