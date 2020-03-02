package br.com.fill.survey.exception;

public class InvalidOptionException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidOptionException(String errorMessage) {
		super(errorMessage);
	}
}
