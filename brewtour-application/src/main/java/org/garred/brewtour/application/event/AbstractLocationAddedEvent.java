package org.garred.brewtour.application.event;

import java.math.BigDecimal;

import org.garred.brewtour.application.AvailableImages;
import org.garred.brewtour.application.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;

public abstract class AbstractLocationAddedEvent extends AbstractLocationEvent {

    public final String brewDbId;
    public final String name;
    public final String description;
    public final BigDecimal latitude;
    public final BigDecimal longitude;
    public final AvailableImages images;

	@JsonCreator
	public AbstractLocationAddedEvent(LocationId locationId,
	        String brewDbId,
	        String name,
	        String description,
	        BigDecimal latitude,
	        BigDecimal longitude,
	        AvailableImages images) {
		super(locationId);
        this.brewDbId = brewDbId;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.images = images;
	}

}
