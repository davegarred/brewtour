package org.garred.skeleton;

import org.garred.skeleton.api.SkelAggregate;
import org.garred.skeleton.api.event.SkelAddedEvent;
import org.garred.skeleton.api.event.SkelValueAddedEvent;
import org.garred.skeleton.repository.SkeletonRepository;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class SkelEventHandler {

	private final SkeletonRepository repository;

	public SkelEventHandler(SkeletonRepository repository, EventBus eventBus) {
		eventBus.register(this);
		this.repository = repository;
	}
	
	@Subscribe
	public void on(SkelAddedEvent event) {
		repository.save(event.id, new SkelAggregate(event.id));
	}
	
	@Subscribe
	public void on(SkelValueAddedEvent event) {
		repository.update(event.id, a -> a.values.add(event.value));
	}
	
	
}
