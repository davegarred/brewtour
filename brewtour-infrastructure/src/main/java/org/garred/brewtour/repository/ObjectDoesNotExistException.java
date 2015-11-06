package org.garred.brewtour.repository;

import static java.lang.String.format;

@SuppressWarnings("serial")
public class ObjectDoesNotExistException extends RuntimeException {

	public ObjectDoesNotExistException(Class<?> clazz, String id) {
		super(message(clazz, id));
	}

	public ObjectDoesNotExistException(Class<?> clazz, String id, Exception e) {
		super(message(clazz, id), e);
	}

	private static String message(Class<?> clazz, String id) {
		return format("Attempt to access object that does not exist, id: %s, type: %s", id, clazz.getCanonicalName());
	}
}
