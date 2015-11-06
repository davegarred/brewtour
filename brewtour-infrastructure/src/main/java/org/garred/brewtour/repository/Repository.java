package org.garred.brewtour.repository;

public interface Repository<I, T> {

	boolean exists(I key);
	T get(I key);
	void save(I key, T value);
	void update(I key, T value);
	void delete(I key);

}
