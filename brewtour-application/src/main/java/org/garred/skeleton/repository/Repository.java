package org.garred.skeleton.repository;

import java.util.function.Consumer;


public interface Repository<I, T> {

	T get(I key);
	void save(I key, T value);
	void put(I key, T value);
	void update(I key, T value);
	void update(I key, Consumer<T> updater);
	void delete(I key);

}
