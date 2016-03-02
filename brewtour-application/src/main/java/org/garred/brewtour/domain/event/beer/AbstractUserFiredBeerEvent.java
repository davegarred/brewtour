package org.garred.brewtour.domain.event.beer;

import org.garred.brewtour.domain.BeerId;
import org.garred.brewtour.domain.UserId;

public class AbstractUserFiredBeerEvent extends AbstractBeerEvent {

	public final UserId userId;
	public final String userScreenName;

	public AbstractUserFiredBeerEvent(BeerId beerId, UserId userId, String userScreenName) {
		super(beerId);
		this.userId = userId;
		this.userScreenName = userScreenName;
	}

}
