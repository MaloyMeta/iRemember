package com.maloy.iremember.repositories.finance;

import com.maloy.iremember.entity.client.Client;
import com.maloy.iremember.entity.finance.Transaction;
import com.maloy.iremember.entity.user.Company;
import com.maloy.iremember.enums.finance.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findAllByCompany(Company company, Pageable pageable);
    Page<Transaction> findAllByClient(Client client, Pageable pageable);
    Optional<Transaction> findByClientAndId(Client client, Long transactionId);

    @Query("""
            SELECT COALESCE(SUM(t.amount), 0)
            FROM Transaction t
            WHERE t.client.id = :clientId
            AND t.company.id = :companyId
            AND t.type = :type
        """)
    BigDecimal getTotalAmountByClientIdAndCompanyIdAndType(
            @Param("clientId") Long clientId,
            @Param("companyId") Long companyId,
            @Param("type") TransactionType type
    );

    @Query("""
            SELECT COALESCE(SUM(t.amount), 0)
            FROM Transaction t
            WHERE t.company.id = :companyId
            AND t.type = :type
        """)
    BigDecimal getTotalAmountByCompanyIdAndType(
            @Param("companyId") Long companyId,
            @Param("type") TransactionType type
    );

    int countByClientIdAndCompanyId(Long clientId, Long companyId);
    int countByCompanyId(Long companyId);

    Optional<Transaction> findFirstByClientIdAndCompanyIdOrderByCreatedAtDescIdDesc(
            Long clientId,
            Long companyId
    );

    Optional<Transaction> findFirstByCompanyIdOrderByCreatedAtDescIdDesc(
            Long companyId
    );

    @Query("""
    SELECT COALESCE(AVG(t.amount), 0)
    FROM Transaction t
    WHERE t.company.id = :companyId
    """)
    BigDecimal getAverageTransactionAmount(@Param("companyId") Long companyId);
}
