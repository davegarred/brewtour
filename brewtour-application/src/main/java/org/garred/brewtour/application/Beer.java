package org.garred.brewtour.application;

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
import org.garred.brewtour.application.command.beer.AddBeerCommand;
import org.garred.brewtour.application.command.beer.AddBeerRatingCommand;
import org.garred.brewtour.application.command.beer.AddBeerReviewCommand;
import org.garred.brewtour.application.command.beer.ModifyBeerCommand;
import org.garred.brewtour.application.command.beer.UpdateBeerImagesCommand;
import org.garred.brewtour.application.event.beer.AbstractBeerReviewAddedEvent;
import org.garred.brewtour.application.event.beer.AbstractBeerStarRatingAddedEvent;
import org.garred.brewtour.application.event.beer.BeerAddedEvent;
import org.garred.brewtour.application.event.beer.BeerImagesUpdatedEvent;
import org.garred.brewtour.application.event.beer.BeerModifiedEvent;
import org.garred.brewtour.application.event.beer.BeerRatingUpdatedEvent;
import org.garred.brewtour.application.event.beer.BeerReviewAddedByAnonymousEvent;
import org.garred.brewtour.application.event.beer.BeerReviewAddedByUserEvent;
import org.garred.brewtour.application.event.beer.BeerStarRatingAddedByAnonymousEvent;
import org.garred.brewtour.application.event.beer.BeerStarRatingAddedByUserEvent;
import org.garred.brewtour.domain.AvailableImages;
import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.LocationId;
import org.garred.brewtour.domain.ReviewMedal;
import org.garred.brewtour.domain.UserId;
import org.garred.brewtour.domain.UserReview;
import org.garred.brewtour.security.UserAuth;

@SuppressWarnings("serial")
public class Beer extends AbstractAnnotatedAggregateRoot<BeerId> {

    @AggregateIdentifier
    private BeerId id;

	private String beerName;

	private LocationId breweryId;
	private String breweryName;
	private String style;
	private String category;
	private BigDecimal abv, ibu;
	private AvailableImages images;
	private final Set<LocationId> availableLocationIds = new HashSet<>();

	private final List<UserReview> reviews = new ArrayList<>();
	private final Map<UserId,ReviewMedal> userMedalRatings = new HashMap<>();

	private ReviewMedal medal;

    public Beer() {}

    public static Beer addBeer(BeerId beerId, AddBeerCommand command) {
    	final Beer beer = new Beer();
    	beer.apply(BeerAddedEvent.fromCommand(command, beerId));
    	return beer;
    }

	public void modifyBeer(ModifyBeerCommand modifyBeer) {
		apply(BeerModifiedEvent.fromCommand(modifyBeer));
	}

	public void updateImages(UpdateBeerImagesCommand command) {
		if(this.images == null || !this.images.equals(command.images)) {
			apply(BeerImagesUpdatedEvent.fromCommand(command));
		}
	}


	public void addBeerStarRating(AddBeerRatingCommand beerReview, UserAuth user) {
		if(user.identified()) {
			apply(BeerStarRatingAddedByUserEvent.fromCommand(beerReview, user.identifier(), user.screenName()));
		} else {
			apply(BeerStarRatingAddedByAnonymousEvent.fromCommand(beerReview, user.identifier()));
		}
		final ReviewMedal updatedMedal = ReviewMedal.average(new ArrayList<>(this.userMedalRatings.values()));
		if(!Objects.equals(updatedMedal, this.medal)) {
			apply(new BeerRatingUpdatedEvent(this.id, updatedMedal));
		}
	}
	public void addBeerReview(AddBeerReviewCommand beerReview, UserAuth user, LocalDateTime time) {
		if(user.identified()) {
			apply(BeerReviewAddedByUserEvent.fromCommand(beerReview, user.identifier(), user.screenName(), time));
		} else {
			apply(BeerReviewAddedByAnonymousEvent.fromCommand(beerReview, user.identifier(), time));
		}
		final ReviewMedal updatedMedal = ReviewMedal.average(new ArrayList<>(this.userMedalRatings.values()));
		if(!Objects.equals(updatedMedal, this.medal)) {
			apply(new BeerRatingUpdatedEvent(this.id, updatedMedal));
		}
	}

	@EventHandler
    public void on(BeerAddedEvent event) {
		this.id = event.beerId;
		this.beerName = event.beerName;
		this.breweryId = event.breweryId;
		this.breweryName = event.breweryName;
		this.style = event.style;
		this.category = event.category;
		this.abv = event.abv;
		this.ibu = event.ibu;
    }

    @EventHandler
    public void on(BeerModifiedEvent event) {
    	this.style = event.style;
    	this.category = event.category;
    	this.abv = event.abv;
    	this.ibu = event.ibu;
    }
    @EventHandler
    public void on(BeerImagesUpdatedEvent event) {
    	this.images = event.images;
    }
    @EventHandler
    public void on(AbstractBeerStarRatingAddedEvent event) {
    	this.userMedalRatings.put(event.userId, event.medal);
    }
    @EventHandler
    public void on(AbstractBeerReviewAddedEvent event) {
    	final UserReview review = new UserReview(event.userId, event.medal, event.time, event.review);
    	this.reviews.add(review);
    	on((AbstractBeerStarRatingAddedEvent)event);
    }

}
