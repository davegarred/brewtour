package org.garred.skeleton.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.garred.skeleton.validation.ConstraintViolationDto;
import org.garred.skeleton.validation.ConstraintViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

public class AbstractRestController {

	private final ObjectMapper objectMapper;
	
	public AbstractRestController() {
		this.objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JSR310Module());
	}
	
	@ExceptionHandler(value = ConstraintViolationException.class)
	@ResponseBody
	@ResponseStatus(value = BAD_REQUEST)
	public String constraintViolation(ConstraintViolationException e) throws JsonProcessingException {
		return objectMapper.writeValueAsString(new ConstraintViolationDto(e.getViolations()));
	}
}
