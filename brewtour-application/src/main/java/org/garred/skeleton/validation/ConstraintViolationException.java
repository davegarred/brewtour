package org.garred.skeleton.validation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

@SuppressWarnings("serial")
//TODO should extend Exception
public class ConstraintViolationException extends RuntimeException {

	private final Map<String,String> violations = new HashMap<>();
	
	public ConstraintViolationException(Set<ConstraintViolation<Object>> validationResult) {
		for(ConstraintViolation<?> violation : validationResult) {
			violations.put(violation.getPropertyPath().toString(), violation.getMessage());
		}
	}
	
	public Map<String,String> getViolations() {
		return violations;
	}

}
