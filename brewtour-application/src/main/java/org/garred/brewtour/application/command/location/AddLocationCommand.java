package org.garred.brewtour.application.command.location;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddLocationCommand {

    public final String brewDbId;
    public final String name;

	@JsonCreator
	public AddLocationCommand(
	        @JsonProperty("brewDbId") String brewDbId,
	        @JsonProperty("name") String name
	        ) {
        this.brewDbId = brewDbId;
        this.name = name;
	}

}
