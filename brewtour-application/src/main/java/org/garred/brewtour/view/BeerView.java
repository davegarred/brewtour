package org.garred.brewtour.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.garred.brewtour.domain.AvailableImages;
import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.Entity;

public class BeerView extends AbstractView implements Entity<BeerId> {

	public BeerId id;
	public String beerName,breweryName,description,style;
	public BigDecimal abv, ibu, srm;
	public AvailableImages images;
	public List<Review> userReviews = new ArrayList<>();

	public String medal;

	public static BeerView newBeerView(BeerId beerId, String beerName, String breweryName) {
		final BeerView view = new BeerView();
		view.id = beerId;
		view.beerName = beerName;
		view.breweryName = breweryName;
		return view;
	}

	public void addReview(Review review) {
		this.userReviews.add(review);
	}

	@Override
	public BeerId identifier() {
		return this.id;
	}

}
