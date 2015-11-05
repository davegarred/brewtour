package org.garred.skeleton.repository;

import static java.lang.String.format;

import org.garred.skeleton.api.SkelAggregate;

@SuppressWarnings("serial")
public class OptimisticLockingException extends RuntimeException {

	public OptimisticLockingException(Class<?> clazz, String id) {
		super(message(clazz, id));
	}

	public OptimisticLockingException(Class<SkelAggregate> clazz, String id, Exception e) {
		super(message(clazz, id), e);
	}

	private static String message(Class<?> clazz, String id) {
		return format("Attempt to update object failed, id: %s, type: %s", id, clazz.getCanonicalName());
	}

}
