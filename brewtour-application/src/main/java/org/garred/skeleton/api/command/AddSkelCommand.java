package org.garred.skeleton.api.command;

import org.garred.skeleton.api.SkelId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddSkelCommand extends AbstractSkelCommand {

	@JsonCreator
	public AddSkelCommand(@JsonProperty("id") SkelId id) {
		super(id);
	}

}
