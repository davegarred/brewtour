package org.garred.brewtour.validation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

@SuppressWarnings("serial")
public class ConstraintViolationException extends RuntimeException {

	private final Map<String,String> violations = new HashMap<>();

	public ConstraintViolationException(Set<ConstraintViolation<Object>> validationResult) {
		for(final ConstraintViolation<?> violation : validationResult) {
			this.violations.put(violation.getPropertyPath().toString(), violation.getMessage());
		}
	}

	public Map<String,String> getViolations() {
		return this.violations;
	}

}
