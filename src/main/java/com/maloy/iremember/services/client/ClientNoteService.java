package com.maloy.iremember.services.client;

import com.maloy.iremember.dto.clientNote.ClientNoteRequest;
import com.maloy.iremember.dto.clientNote.ClientNoteResponse;
import com.maloy.iremember.entity.client.Client;
import com.maloy.iremember.entity.client.ClientNote;
import com.maloy.iremember.entity.user.User;
import com.maloy.iremember.enums.user.UserRole;
import com.maloy.iremember.exceptions.client.ClientNotFoundException;
import com.maloy.iremember.exceptions.client.ClientNoteAlreadyExistsException;
import com.maloy.iremember.exceptions.client.ClientNoteNotFoundException;
import com.maloy.iremember.exceptions.user.PermissionDeniedException;
import com.maloy.iremember.repositories.client.ClientNoteRepository;
import com.maloy.iremember.repositories.client.ClientRepository;
import com.maloy.iremember.repositories.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientNoteService {

    private final ClientNoteRepository clientNoteRepository;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    //CREATE
    @Transactional
    public ClientNoteResponse createNote(ClientNoteRequest request, Long currentClientId, User user){
        Client client = clientRepository.findByIdAndCompany(currentClientId,user.getCompany())
                .orElseThrow(()-> new ClientNotFoundException("Client with id: " + currentClientId + "not found"));

        if (clientNoteRepository.findByOwnerNoteAndClient(user, client).isPresent()) {
            throw new ClientNoteAlreadyExistsException("Note already exists, use update instead");
        }

        ClientNote clientNote = new ClientNote();
        clientNote.setClient(client);
        clientNote.setText(request.text());
        clientNote.setOwnerNote(user);

        ClientNote savedNote = clientNoteRepository.save(clientNote);

        return ClientNoteResponse.fromEntity(savedNote);
    }

    //READ
    public List<ClientNoteResponse> getAllNotesByClientId(Long clientId, User user){
        Client client = clientRepository.findByIdAndCompany(clientId,user.getCompany())
                .orElseThrow(()-> new ClientNotFoundException("Client with id: " + clientId + "not found"));

        List<ClientNote> clientNotes = clientNoteRepository.findByClient(client);

        return clientNotes.stream()
                .map(ClientNoteResponse::fromEntity)
                .toList();
    }

    public List<ClientNoteResponse> getAllNotesByCurrentUser(User user){

        List<ClientNote> userNotes = clientNoteRepository.findByOwnerNote(user);

        return userNotes.stream()
                .map(ClientNoteResponse::fromEntity)
                .toList();
    }

    //Update
    @Transactional
    public ClientNoteResponse updateClientNote(ClientNoteRequest request, Long clientId, User user){
        Client client = clientRepository.findByIdAndCompany(clientId,user.getCompany())
                .orElseThrow(()-> new ClientNotFoundException("Client with id: " + clientId + "not found"));

        ClientNote clientNote = clientNoteRepository.findByOwnerNoteAndClient(user, client)
                .orElseThrow(()-> new RuntimeException("заглушка"));

        clientNote.setText(request.text());

        ClientNote saved = clientNoteRepository.save(clientNote);

        return ClientNoteResponse.fromEntity(saved);
    }

    //Delete
    @Transactional
    public void deleteNote(Long clientId, Long noteId, User user){
        Client client = clientRepository.findByIdAndCompany(clientId, user.getCompany())
                .orElseThrow(() -> new ClientNotFoundException("Client with id: " + clientId + " not found"));

        ClientNote clientNote = clientNoteRepository.findByIdAndClient(noteId, client)
                .orElseThrow(() -> new ClientNoteNotFoundException("Note not found"));

        boolean isOwner = clientNote.getOwnerNote().getId().equals(user.getId());
        boolean isAdmin = user.getRole() == UserRole.ADMIN;

        if (!isOwner && !isAdmin) {
            throw new PermissionDeniedException("Note does not belong to you");
        }

        clientNoteRepository.delete(clientNote);
    }
}
