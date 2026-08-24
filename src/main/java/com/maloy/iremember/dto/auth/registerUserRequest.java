package com.maloy.iremember.dto.auth;

public record registerUserRequest(
        String username,
        String email,
        String firstName,
        String lastName,
        String password
) {
}
