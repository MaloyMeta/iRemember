package com.maloy.iremember.controllers.client;

import com.maloy.iremember.dto.client.ClientRequest;
import com.maloy.iremember.dto.client.ClientResponse;
import com.maloy.iremember.dto.client.ClientUpdateRequest;
import com.maloy.iremember.entity.user.Company;
import com.maloy.iremember.entity.user.User;
import com.maloy.iremember.security.CustomUserDetails;
import com.maloy.iremember.services.client.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<Page<ClientResponse>> getClients(
            @AuthenticationPrincipal CustomUserDetails currentUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
            ){
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Company company = currentUser.getUser().getCompany();
        return ResponseEntity.ok(clientService.getClients(company, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getClient(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        Company company = currentUser.getUser().getCompany();
        return ResponseEntity.ok(clientService.getClientById(company, id));
    }

    @PostMapping
    public ResponseEntity<ClientResponse> createClient(
            @RequestBody ClientRequest client,
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        Company company = currentUser.getUser().getCompany();
        User manager = currentUser.getUser();
        return ResponseEntity.ok(clientService.createClient(client, company, manager));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> updateClient(
            @PathVariable Long id,
            @RequestBody ClientUpdateRequest client,
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        Company company = currentUser.getUser().getCompany();
        return ResponseEntity.ok(clientService.updateClient(company, id, client, currentUser.getUser()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        Company company = currentUser.getUser().getCompany();
        clientService.deleteClient(company, id, currentUser.getUser());
        return ResponseEntity.noContent().build();
    }
}
