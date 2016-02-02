package org.garred.brewtour.service;

import java.util.HashMap;
import java.util.Map;

import org.garred.brewtour.domain.Entity;
import org.garred.brewtour.domain.Identifier;
import org.garred.brewtour.repository.ObjectDoesNotExistException;
import org.garred.brewtour.repository.ViewRepository;

public abstract class AbstractRepositoryStub<I extends Identifier,T extends Entity<I>> implements ViewRepository<I,T> {

	private final Map<I,T> objectMap = new HashMap<>();

	protected abstract Class<T> objectClass();

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
		final I key = value.identifier();
		if(exists(key)) {
			throw new IllegalStateException();
		}
		this.objectMap.put(key, value);
	}

	@Override
	public void update(T value) {
		final I key = value.identifier();
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
			throw new ObjectDoesNotExistException(objectClass(), key.getId());
		}
		return get(key);
	}

	public Map<I,T> objectMap() {
		return this.objectMap;
	}
}
