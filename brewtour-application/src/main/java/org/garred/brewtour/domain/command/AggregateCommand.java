package org.garred.brewtour.domain.command;

import org.garred.brewtour.domain.AbstractIdentifier;

public interface AggregateCommand<I extends AbstractIdentifier> {

	I identifier();

}
