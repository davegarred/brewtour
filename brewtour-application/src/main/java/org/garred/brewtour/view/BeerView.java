package org.garred.brewtour.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BeerView extends AbstractView {

	public String name,style,category;
	public BigDecimal abv, ibu;
	public boolean available;
	public List<Review> userReviews;

	public String medal;

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

	public void addReview(Review review) {
		this.userReviews.add(review);
	}

}
