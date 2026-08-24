package com.maloy.iremember.exceptions;

public class ClientNoteAlreadyExistsException extends RuntimeException {
    public ClientNoteAlreadyExistsException(String message) {
        super(message);
    }
}
