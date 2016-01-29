package org.garred.brewtour.application.event.location;

import java.math.BigDecimal;
import java.util.List;

import org.garred.brewtour.application.command.location.AddPopulatedLocationCommand;
import org.garred.brewtour.domain.AvailableImages;
import org.garred.brewtour.domain.Beer;
import org.garred.brewtour.domain.LocationId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PopulatedLocationAddedEvent extends AbstractLocationAddedEvent {

    public final String description;
    public final BigDecimal latitude;
    public final BigDecimal longitude;
    public final AvailableImages images;
    public final List<Beer> beers;


	@JsonCreator
	public PopulatedLocationAddedEvent(@JsonProperty("locationId") LocationId locationId,
	        @JsonProperty("brewDbId") String brewDbId,
	        @JsonProperty("name") String name,
	        @JsonProperty("description") String description,
	        @JsonProperty("latitude") BigDecimal latitude,
	        @JsonProperty("longitude") BigDecimal longitude,
	        @JsonProperty("images") AvailableImages images,
	        @JsonProperty("beers") List<Beer> beers) {
		super(locationId, brewDbId, name);
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.images = images;
        this.beers = beers;
	}

	public static PopulatedLocationAddedEvent fromCommand(LocationId locationId, AddPopulatedLocationCommand command) {
		return new PopulatedLocationAddedEvent(locationId, command.brewDbId, command.name,
				command.description, command.latitude, command.longitude, command.images, command.beers);
	}

}
