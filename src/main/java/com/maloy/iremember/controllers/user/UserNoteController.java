package com.maloy.iremember.controllers;

import com.maloy.iremember.dto.clientNote.ClientNoteResponse;
import com.maloy.iremember.security.CustomUserDetails;
import com.maloy.iremember.services.ClientNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class UserNoteController {

    private final ClientNoteService clientNoteService;

    @GetMapping("/my")
    public ResponseEntity<List<ClientNoteResponse>> getAllNotesByCurrentUser(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(clientNoteService.getAllNotesByCurrentUser(user.getUser()));
    }
}