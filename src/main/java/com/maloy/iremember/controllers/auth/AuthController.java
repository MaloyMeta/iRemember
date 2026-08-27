package com.maloy.iremember.controllers;

import com.maloy.iremember.dto.auth.AuthenticationResponse;
import com.maloy.iremember.dto.auth.LoginUserRequest;
import com.maloy.iremember.dto.auth.registerCompanyRequest;
import com.maloy.iremember.dto.auth.registerUserRequest;
import com.maloy.iremember.entity.Company;
import com.maloy.iremember.entity.User;
import com.maloy.iremember.enums.UserRole;
import com.maloy.iremember.repositories.CompanyRepository;
import com.maloy.iremember.repositories.UserRepository;
import com.maloy.iremember.security.CustomUserDetails;
import com.maloy.iremember.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register-company")
    public ResponseEntity<AuthenticationResponse> registerCompany(@RequestBody registerCompanyRequest request) {

        if (companyRepository.existsByCompanyName(request.companyName())) {
            throw new IllegalArgumentException("Компания с таким названием уже существует");
        }
        if (userRepository.existsByEmail(request.companyEmail())) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует");
        }

        Company company = new Company();
        company.setCompanyName(request.companyName());
        company.setCompanyEmail(request.companyEmail());
        companyRepository.save(company);

        User admin = new User();
        admin.setUsername(request.username());
        admin.setEmail(request.companyEmail());
        admin.setFirstName(request.firstName());
        admin.setLastName(request.lastName());
        admin.setPassword(passwordEncoder.encode(request.password()));
        admin.setRole(UserRole.ADMIN);
        admin.setCompany(company);
        userRepository.save(admin);

        String token = jwtService.generateToken(new CustomUserDetails(admin));
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @PostMapping("/register-user")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody registerUserRequest request) {

        CustomUserDetails currentAdmin = (CustomUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        Company company = currentAdmin.getUser().getCompany();

        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(UserRole.EMPLOYEE);
        user.setCompany(company);
        userRepository.save(user);

        String token = jwtService.generateToken(new CustomUserDetails(user));
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginUserRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        String token = jwtService.generateToken(new CustomUserDetails(user));
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

}
