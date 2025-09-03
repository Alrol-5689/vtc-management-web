package com.vtc.util;

import com.vtc.exceptions.InvalidDNIException;
import com.vtc.exceptions.InvalidPINException;

public class Validator {
	
	public void validatePin(String pin) throws InvalidPINException {
		if (pin == null || !pin.matches("\\d{4}")) {
			throw new InvalidPINException("el PIN debe tener 4 dígitos.");
		}
	}
	
	public void validateDNIFormat(String dni) throws InvalidDNIException {
		if (!dni.matches("\\d{8}[A-Za-z]")) {
			throw new InvalidDNIException("formato incorrecto.");
		}
	}

}
