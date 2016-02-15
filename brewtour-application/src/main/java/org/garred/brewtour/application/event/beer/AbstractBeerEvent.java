package org.garred.brewtour.application.event.beer;

import org.garred.brewtour.domain.BeerId;

public abstract class AbstractBeerEvent {

	public final BeerId beerId;

	public AbstractBeerEvent(BeerId beerId) {
		this.beerId = beerId;
	}

}
