package com.maloy.iremember.dto.client;

import com.maloy.iremember.entity.client.Client;
import com.maloy.iremember.enums.client.ClientStatus;

public record ClientResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        String phone,
        ClientStatus status,
        String emailLinkedManager
) {
    public static ClientResponse fromEntity(Client client) {
        return new ClientResponse(
                client.getId(),
                client.getEmail(),
                client.getFirstName(),
                client.getLastName(),
                client.getPhone(),
                client.getStatus(),
                client.getLinkedManager().getEmail()
        );
    }
}
