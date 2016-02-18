package org.garred.brewtour.application.command;

import org.garred.brewtour.domain.AbstractIdentifier;

public interface AddAggregateCommand<I extends AbstractIdentifier> {

	void subscribe(AddAggregateCallback<I> callback);
	void identified(I identifier);

}
