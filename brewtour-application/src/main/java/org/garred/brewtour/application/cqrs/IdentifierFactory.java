package org.garred.brewtour.application.cqrs;

import org.garred.brewtour.application.AbstractIdentifier;

public interface IdentifierFactory<T extends AbstractIdentifier> {

	T next();

}
