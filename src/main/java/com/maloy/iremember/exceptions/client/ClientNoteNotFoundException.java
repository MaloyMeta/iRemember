package com.maloy.iremember.exceptions.client;

public class ClientNoteNotFoundException extends RuntimeException {
    public ClientNoteNotFoundException(String message) {
        super(message);
    }
}
