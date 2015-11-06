package org.garred.brewtour.validation;

import java.util.Map;

public class ConstraintViolationDto {

	private final Map<String,String> violations;
	
	public ConstraintViolationDto(Map<String,String> violations) {
		this.violations = violations;
	}
	
	public Map<String,String> getViolations() {
		return violations;
	}

}
