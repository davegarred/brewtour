package org.garred.brewtour.view;

import java.util.function.Consumer;

import org.garred.brewtour.application.Entity;
import org.garred.brewtour.application.Identifier;
import org.garred.brewtour.repository.ViewRepository;

public abstract class AbstractViewEventHandler<I extends Identifier, T extends Entity<I>> {

	protected final ViewRepository<I,T> repository;

	public AbstractViewEventHandler(ViewRepository<I, T> repository) {
		this.repository = repository;
	}

	protected T require(I identifier) {
		final T entity = this.repository.get(identifier);
    	if(entity == null) {
    		throw new RuntimeException("No view found for id " + identifier);
    	}
		return entity;
	}

	protected void persist(T location) {
		this.repository.update(location);
	}

	protected void update(I identifier, Consumer<T> consumer) {
		final T view = require(identifier);
		consumer.accept(view);
		persist(view);
	}

}
