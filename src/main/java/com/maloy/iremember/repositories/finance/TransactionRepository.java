package com.maloy.iremember.repositories.finance;

import com.maloy.iremember.entity.client.Client;
import com.maloy.iremember.entity.finance.Transaction;
import com.maloy.iremember.entity.user.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findAllByCompany(Company company, Pageable pageable);
    Page<Transaction> findAllByClient(Client client, Pageable pageable);
    Optional<Transaction> findByClientAndId(Client client, Long transactionId);
}
