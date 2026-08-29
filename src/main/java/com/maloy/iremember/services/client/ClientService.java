package com.maloy.iremember.services.client;

import com.maloy.iremember.dto.client.ClientRequest;
import com.maloy.iremember.dto.client.ClientResponse;
import com.maloy.iremember.dto.client.ClientUpdateRequest;
import com.maloy.iremember.entity.client.Client;
import com.maloy.iremember.entity.user.Company;
import com.maloy.iremember.entity.user.User;
import com.maloy.iremember.enums.client.ClientStatus;
import com.maloy.iremember.exceptions.client.ClientNotFoundException;
import com.maloy.iremember.exceptions.user.UserNotFoundException;
import com.maloy.iremember.repositories.client.ClientRepository;
import com.maloy.iremember.repositories.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.maloy.iremember.services.utils.ValidatePermissionUtil.validatePermission;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    public Page<ClientResponse> getClients(Company company, Pageable pageable){
        return clientRepository.findAllByCompany(company, pageable)
                .map(ClientResponse::fromEntity);
    }

    public ClientResponse getClientById(Company company, Long clientId){
        Client client = clientRepository.findByIdAndCompany(clientId,company)
                .orElseThrow(()-> new ClientNotFoundException("Client with id: " + clientId + "not found"));
        return ClientResponse.fromEntity(client);
    }

    @Transactional
    public ClientResponse createClient(ClientRequest clientRequest, Company company, User currentUser) {
        Client client = new Client();
        client.setEmail(clientRequest.email());
        client.setFirstName(clientRequest.firstName());
        client.setLastName(clientRequest.lastName());
        client.setPhone(clientRequest.phone());
        client.setStatus(ClientStatus.NEW);
        client.setCompany(company);
        client.setLinkedManager(currentUser);

        Client saved = clientRepository.save(client);
        return ClientResponse.fromEntity(saved);
    }

    @Transactional
    public ClientResponse updateClient(Company company, Long clientId, ClientUpdateRequest request, User currentUser){
        Client client = clientRepository.findByIdAndCompany(clientId,company)
                .orElseThrow(()-> new ClientNotFoundException("Client with id: " + clientId + "not found"));

        validatePermission(currentUser, client);

        if(request.email() != null){
            client.setEmail(request.email());
        }
        if(request.firstName() != null){
            client.setFirstName(request.firstName());
        }
        if(request.lastName() != null){
            client.setLastName(request.lastName());
        }
        if(request.phone() != null){
            client.setPhone(request.phone());
        }
        if(request.status() != null){
            client.setStatus(request.status());
        }
        if(request.managerEmail() !=null){
            User user = userRepository.findByEmail(request.managerEmail())
                    .orElseThrow(()-> new UserNotFoundException("User with email: "+ request.managerEmail() + " not found"));
            client.setLinkedManager(user);
        }

        Client saved = clientRepository.save(client);
        return ClientResponse.fromEntity(saved);
    }

    @Transactional
    public void deleteClient(Company company ,Long clientId, User currentUser) {
        Client client = clientRepository.findByIdAndCompany(clientId, company)
                .orElseThrow(() -> new ClientNotFoundException("Клиент с id " + clientId + " не найден"));

        validatePermission(currentUser, client);

        clientRepository.delete(client);
    }
}
