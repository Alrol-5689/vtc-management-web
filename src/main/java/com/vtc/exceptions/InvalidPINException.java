package com.vtc.exceptions;

public class InvalidPINException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidPINException(String mensaje) {
		super(mensaje);
	}

}
