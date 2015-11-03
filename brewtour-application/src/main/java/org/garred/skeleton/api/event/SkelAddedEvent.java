package org.garred.skeleton.api.event;

import org.garred.skeleton.api.SkelId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SkelAddedEvent extends AbstractSkelEvent {

	@JsonCreator
	public SkelAddedEvent(@JsonProperty("id") SkelId id) {
		super(id);
	}

}
