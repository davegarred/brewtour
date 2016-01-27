package org.garred.brewtour.api;

import org.garred.brewtour.application.AbstractIdentifier;

public interface AggregateCommand<I extends AbstractIdentifier> {

	I identifier();

}
