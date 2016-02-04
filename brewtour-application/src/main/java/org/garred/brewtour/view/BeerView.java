package org.garred.brewtour.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.garred.brewtour.domain.UserReview;

public class BeerView extends AbstractView {

	public String name,style,category;
	public BigDecimal abv, ibu;
	public boolean available;
	public List<Review> userReviews;

	public BigDecimal averageStars;

	public static BeerView newBeerView(String name, String style, String category,
			BigDecimal abv, BigDecimal ibu, boolean available) {
		final BeerView view = new BeerView();
		view.name = name;
		view.style = style;
		view.category = category;
		view.abv = abv;
		view.ibu = ibu;
		view.name = name;
		view.available = available;
		view.userReviews = new ArrayList<>();
		return view;
	}

	public void addReview(UserReview review) {
		this.userReviews.add(new Review(review.stars, review.review));
	}

}
