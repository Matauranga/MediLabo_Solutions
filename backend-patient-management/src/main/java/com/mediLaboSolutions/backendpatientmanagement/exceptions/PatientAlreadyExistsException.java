package com.mediLaboSolutions.backendpatientmanagement.exceptions;

public class PatientAlreadyExistsException extends RuntimeException {
    public PatientAlreadyExistsException(String message) {
        super(message);
    }
}
