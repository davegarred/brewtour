package org.garred.brewtour.service;

import java.util.HashMap;
import java.util.Map;

import org.garred.brewtour.application.Entity;
import org.garred.brewtour.application.Identifier;
import org.garred.brewtour.application.UserDetails;
import org.garred.brewtour.repository.ObjectDoesNotExistException;
import org.garred.brewtour.repository.Repository;

public abstract class AbstractRepositoryStub<I extends Identifier,T extends Entity<I>> implements Repository<I,T> {

	private final Map<I,T> objectMap = new HashMap<>();

	@Override
	public boolean exists(I key) {
		return this.objectMap.containsKey(key);
	}
	@Override
	public T get(I key) {
		return this.objectMap.get(key);
	}

	@Override
	public void save(T value) {
		final I key = value.getIdentifier();
		if(exists(key)) {
			throw new IllegalStateException();
		}
		this.objectMap.put(key, value);
	}

	@Override
	public void update(T value) {
		final I key = value.getIdentifier();
		if(!exists(key)) {
			throw new IllegalStateException();
		}
		this.objectMap.put(key, value);
	}

	@Override
	public void delete(I key) {
		this.objectMap.remove(key);
	}

	@Override
	public T require(I key) throws ObjectDoesNotExistException {
		if(!exists(key)) {
			throw new ObjectDoesNotExistException(UserDetails.class, key.getId());
		}
		return get(key);
	}

}
