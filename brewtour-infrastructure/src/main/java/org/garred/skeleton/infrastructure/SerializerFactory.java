package org.garred.skeleton.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SerializerFactory {

	public static ObjectMapper infrastructureSerializer() {
		final ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper;
	}
}
