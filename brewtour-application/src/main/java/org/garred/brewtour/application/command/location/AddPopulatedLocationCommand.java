package org.garred.brewtour.application.command.location;

import java.math.BigDecimal;
import java.util.List;

import org.garred.brewtour.domain.AvailableImages;
import org.garred.brewtour.domain.Beer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddPopulatedLocationCommand {

    public final String brewDbId;
    public final String name;
    public final String description;
    public final BigDecimal latitude;
    public final BigDecimal longitude;
    public final AvailableImages images;
    public final List<Beer> beers;

	@JsonCreator
	public AddPopulatedLocationCommand(
	        @JsonProperty("brewDbId") String brewDbId,
	        @JsonProperty("name") String name,
	        @JsonProperty("description") String description,
	        @JsonProperty("latitude") BigDecimal latitude,
	        @JsonProperty("longitude") BigDecimal longitude,
	        @JsonProperty("images") AvailableImages images,
	        @JsonProperty("beers") List<Beer> beers
	        ) {
        this.brewDbId = brewDbId;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.images = images;
        this.beers = beers;
	}

}
