package org.garred.skeleton.api.event;

import org.garred.skeleton.api.SkelId;

import com.nullgeodesic.cqrs.api.Event;

public class AbstractSkelEvent implements Event<SkelId> {

	public final SkelId id;

	public AbstractSkelEvent(SkelId id) {
		this.id = id;
	}

	@Override
	public SkelId aggregateId() {
		return id;
	}
	
}
