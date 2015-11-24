package org.garred.brewtour.repository;

import org.garred.brewtour.application.Entity;
import org.garred.brewtour.application.Identifier;

public interface Repository<I extends Identifier, T extends Entity<I>> {

	boolean exists(I key);
	T get(I key);
	T require(I key) throws ObjectDoesNotExistException;
	void save(T value);
	void update(T value) throws ObjectDoesNotExistException;
	void delete(I key);

}
