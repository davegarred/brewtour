package org.garred.brewtour.application.cqrs;

import org.axonframework.repository.Repository;
import org.garred.brewtour.api.AggregateCommand;
import org.garred.brewtour.application.AbstractIdentifier;

public abstract class AbstractCommandHandler<I extends AbstractIdentifier,T> {

	protected final Repository<T> repository;
	public AbstractCommandHandler(Repository<T> repository) {
		this.repository = repository;
	}

	protected T require(AggregateCommand<I> command) {
		final AbstractIdentifier identifier = command.identifier();
		final T aggregate = this.repository.load(identifier);
		if(aggregate == null) {
			throw new RuntimeException("Could not find an aggregate with id " + identifier);
		}
		return aggregate;
	}

}
