package com.maloy.iremember.controllers.client;

import com.maloy.iremember.dto.clientNote.ClientNoteRequest;
import com.maloy.iremember.dto.clientNote.ClientNoteResponse;
import com.maloy.iremember.security.CustomUserDetails;
import com.maloy.iremember.services.client.ClientNoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients/{clientId}/notes")
@RequiredArgsConstructor
public class ClientNoteController {

    private final ClientNoteService clientNoteService;

    @PostMapping
    public ResponseEntity<ClientNoteResponse> createNote(
            @PathVariable Long clientId,
            @Valid @RequestBody ClientNoteRequest request,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        ClientNoteResponse response = clientNoteService.createNote(request, clientId, user.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ClientNoteResponse>> getAllNotesByClientId(
            @PathVariable Long clientId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(clientNoteService.getAllNotesByClientId(clientId, user.getUser()));
    }

    @PutMapping
    public ResponseEntity<ClientNoteResponse> updateClientNote(
            @PathVariable Long clientId,
            @Valid @RequestBody ClientNoteRequest request,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(clientNoteService.updateClientNote(request, clientId, user.getUser()));
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(
            @PathVariable Long clientId,
            @PathVariable Long noteId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        clientNoteService.deleteNote(clientId, noteId, user.getUser());
        return ResponseEntity.noContent().build();
    }
}
