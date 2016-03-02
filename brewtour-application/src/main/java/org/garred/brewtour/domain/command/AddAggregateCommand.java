package org.garred.brewtour.domain.command;

import org.garred.brewtour.domain.AbstractIdentifier;

public interface AddAggregateCommand<I extends AbstractIdentifier> {

	void subscribe(AddAggregateCallback<I> callback);
	void identified(I identifier);

}
