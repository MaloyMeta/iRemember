package com.maloy.iremember.dto.auth;

public record LoginUserRequest(
        String email,
        String password
) {
}
