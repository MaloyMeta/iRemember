package com.maloy.iremember.dto.client;


public record ClientRequest(
        String email,
        String firstName,
        String lastName,
        String phone
) {
}
