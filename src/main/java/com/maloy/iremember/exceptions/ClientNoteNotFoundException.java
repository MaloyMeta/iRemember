package com.maloy.iremember.exceptions;

public class ClientNoteNotFoundException extends RuntimeException {
    public ClientNoteNotFoundException(String message) {
        super(message);
    }
}
