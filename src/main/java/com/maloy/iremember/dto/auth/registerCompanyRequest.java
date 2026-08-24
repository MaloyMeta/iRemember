package com.maloy.iremember.dto.auth;

public record registerCompanyRequest(
        String companyName,
        String companyEmail,
        String username,
        String firstName,
        String lastName,
        String password
) {
}
