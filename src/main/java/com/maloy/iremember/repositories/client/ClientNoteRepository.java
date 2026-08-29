package com.maloy.iremember.repositories.client;

import com.maloy.iremember.entity.client.Client;
import com.maloy.iremember.entity.client.ClientNote;
import com.maloy.iremember.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientNoteRepository extends JpaRepository<ClientNote,Long> {
    List<ClientNote> findByClient(Client client);
    List<ClientNote> findByOwnerNote(User user);
    Optional<ClientNote> findByOwnerNoteAndClient(User user, Client client);
    Optional<ClientNote> findByIdAndClient(Long noteId, Client client);
}
