package com.maloy.iremember.dto.clientNote;

import com.maloy.iremember.entity.ClientNote;

public record ClientNoteResponse(
        String text,
        String ownerEmail,
        String fullNameClient
) {
    public static ClientNoteResponse fromEntity(ClientNote clientNote){
        String fullName = clientNote.getClient().getFirstName() + " " + clientNote.getClient().getLastName();
        return new ClientNoteResponse(
                clientNote.getText(),
                clientNote.getOwnerNote().getEmail(),
                fullName
        );
    }
}
