package org.garred.skeleton.validation;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.garred.skeleton.api.SkelId;
import org.garred.skeleton.api.command.AddValueSkelCommand;
import org.junit.Test;

public class ValidationTest {

	private static final SkelId ID = new SkelId("S001");

	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	@Test
	public void testValid() {
		Set<ConstraintViolation<AddValueSkelCommand>> result = validator.validate(new AddValueSkelCommand(ID, "some value"));
		assertEquals(0, result.size());
	}
	
	@Test
	public void testInvalid() {
		Set<ConstraintViolation<AddValueSkelCommand>> result = validator.validate(new AddValueSkelCommand(ID, ""));
		assertEquals(1, result.size());
		ConstraintViolation<AddValueSkelCommand> singleViolation = result.iterator().next();
		assertEquals("may not be empty", singleViolation.getMessage());
	}
}
