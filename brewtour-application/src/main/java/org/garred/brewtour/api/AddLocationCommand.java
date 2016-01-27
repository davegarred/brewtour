package org.garred.brewtour.api;

import java.math.BigDecimal;

import org.garred.brewtour.application.AvailableImages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddLocationCommand {

    public final String brewDbId;
    public final String name;
    public final String description;
    public final BigDecimal latitude;
    public final BigDecimal longitude;
    public final AvailableImages images;

	@JsonCreator
	public AddLocationCommand(
	        @JsonProperty("brewDbId") String brewDbId,
	        @JsonProperty("name") String name,
	        @JsonProperty("description") String description,
	        @JsonProperty("latitude") BigDecimal latitude,
	        @JsonProperty("longitude") BigDecimal longitude,
	        @JsonProperty("images") AvailableImages images
	        ) {
        this.brewDbId = brewDbId;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.images = images;
	}

}
