package com.maloy.iremember.repositories.client;

import com.maloy.iremember.entity.client.Client;
import com.maloy.iremember.entity.user.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Page<Client> findAllByCompany(Company company, Pageable pageable);
    Optional<Client> findByIdAndCompany(Long id, Company company);

    boolean existsByIdAndCompanyId(Long clientId, Long companyId);

    int countByCompanyId(Long companyId);
}
