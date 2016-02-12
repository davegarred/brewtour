package org.garred.brewtour.view;

public class LocationUserCombinedView {

	public final LocationView location;
	public final UserDetailsView user;

	public LocationUserCombinedView(LocationView location, UserDetailsView userDetailsView) {
		this.location = location;
		this.user = userDetailsView;
	}

}
