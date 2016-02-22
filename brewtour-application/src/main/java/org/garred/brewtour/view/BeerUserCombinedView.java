package org.garred.brewtour.view;

public class BeerUserCombinedView {

	public final BeerView beer;
	public final UserDetailsView user;

	public BeerUserCombinedView(BeerView beer, UserDetailsView userDetailsView) {
		this.beer = beer;
		this.user = userDetailsView;
	}

}
