package com.mediLaboSolutions.backendpatientmanagement.exceptions;

public class PatientNotFoundException extends RuntimeException {

    public PatientNotFoundException(String message) {
        super(message);
    }

}
