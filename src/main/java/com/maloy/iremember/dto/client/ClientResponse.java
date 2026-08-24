package com.maloy.iremember.dto.client;

import com.maloy.iremember.entity.Client;
import com.maloy.iremember.enums.ClientStatus;

public record ClientResponse(
        String email,
        String firstName,
        String lastName,
        String phone,
        ClientStatus status,
        String emailLinkedManager
) {
    public static ClientResponse fromEntity(Client client) {
        return new ClientResponse(
                client.getFirstName(),
                client.getLastName(),
                client.getEmail(),
                client.getPhone(),
                client.getStatus(),
                client.getLinkedManager().getEmail()
        );
    }
}
