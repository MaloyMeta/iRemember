package com.maloy.iremember.repositories;

import com.maloy.iremember.entity.Client;
import com.maloy.iremember.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Page<Client> findAllByCompany(Company company, Pageable pageable);
    Optional<Client> findByIdAndCompany(Long id, Company company);
}
