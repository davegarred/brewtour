package org.garred.skeleton.api.event;

import org.garred.skeleton.api.SkelId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SkelValueAddedEvent extends AbstractSkelEvent {

	public final String value;
	
	@JsonCreator
	public SkelValueAddedEvent(@JsonProperty("id") SkelId id,
			@JsonProperty("value") String value) {
		super(id);
		this.value = value;
	}

}
