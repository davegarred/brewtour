package org.garred.brewtour.domain.event.beer;

import org.garred.brewtour.domain.BeerId;

public abstract class AbstractBeerEvent {

	public final BeerId beerId;

	public AbstractBeerEvent(BeerId beerId) {
		this.beerId = beerId;
	}

}
