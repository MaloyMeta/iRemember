package com.maloy.iremember.repositories;

import com.maloy.iremember.entity.Client;
import com.maloy.iremember.entity.ClientNote;
import com.maloy.iremember.entity.User;
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
