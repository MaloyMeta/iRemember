package com.maloy.iremember.dto.client;

import com.maloy.iremember.enums.ClientStatus;

public record ClientUpdateRequest(
        String email,
        String firstName,
        String lastName,
        String phone,
        ClientStatus status,
        String managerEmail
) {
}
