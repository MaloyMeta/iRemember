package com.maloy.iremember.exceptions.client;

public class ClientNoteAlreadyExistsException extends RuntimeException {
    public ClientNoteAlreadyExistsException(String message) {
        super(message);
    }
}
