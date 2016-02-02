package org.garred.brewtour.application.event.location;

import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;

public abstract class AbstractLocationAddedEvent extends AbstractLocationEvent {

    public final String name;

	@JsonCreator
	public AbstractLocationAddedEvent(LocationId locationId, String name) {
		super(locationId);
        this.name = name;
	}

}
