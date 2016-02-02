package org.garred.brewtour.application.command.location;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddLocationCommand {

    public final String name;

	@JsonCreator
	public AddLocationCommand(@JsonProperty("name") String name) {
        this.name = name;
	}

}
