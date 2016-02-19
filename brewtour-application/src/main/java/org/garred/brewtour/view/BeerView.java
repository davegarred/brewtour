package org.garred.brewtour.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.garred.brewtour.domain.AvailableImages;
import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.Entity;

public class BeerView extends AbstractView implements Entity<BeerId> {

	public BeerId id;
	public String beerName,description,style,category;
	public BigDecimal abv, ibu;
	public AvailableImages images;
	public List<Review> userReviews;

	public String medal;

	public static BeerView newBeerView(BeerId beerId, String beerName, String description, String style, String category,
			BigDecimal abv, BigDecimal ibu) {
		final BeerView view = new BeerView();
		view.id = beerId;
		view.beerName = beerName;
		view.description = description;
		view.style = style;
		view.category = category;
		view.abv = abv;
		view.ibu = ibu;
		view.beerName = beerName;
		view.userReviews = new ArrayList<>();
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
