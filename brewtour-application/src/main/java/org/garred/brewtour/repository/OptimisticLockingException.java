package org.garred.brewtour.repository;

import static java.lang.String.format;

@SuppressWarnings("serial")
public class OptimisticLockingException extends RuntimeException {

	public OptimisticLockingException(Class<?> clazz, String id) {
		super(message(clazz, id));
	}

	public OptimisticLockingException(Class<?> clazz, String id, Exception e) {
		super(message(clazz, id), e);
	}

	private static String message(Class<?> clazz, String id) {
		return format("Attempt to update object failed, id: %s, type: %s", id, clazz.getCanonicalName());
	}

}
