package com.vtc.exceptions;

public class InvalidPhoneNumberException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidPhoneNumberException(String mensaje) {
		super(mensaje);
	}

}
