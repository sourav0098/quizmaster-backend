package com.quizmaster.exceptions;

public class ConstraintViolationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ConstraintViolationException() {
		super("Unique constraint voilation");
	}

	public ConstraintViolationException(String message) {
		super(message);
	}
}
