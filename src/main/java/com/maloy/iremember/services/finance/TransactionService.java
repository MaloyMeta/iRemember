package com.maloy.iremember.services.finance;

import com.maloy.iremember.dto.finance.*;
import com.maloy.iremember.entity.client.Client;
import com.maloy.iremember.entity.finance.Transaction;
import com.maloy.iremember.enums.finance.TransactionType;
import com.maloy.iremember.exceptions.client.ClientNotFoundException;
import com.maloy.iremember.exceptions.finance.TransactionNotFoundException;
import com.maloy.iremember.repositories.client.ClientRepository;
import com.maloy.iremember.repositories.finance.TransactionRepository;
import com.maloy.iremember.security.CustomUserDetails;
import com.maloy.iremember.services.utils.ValidatePermissionUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final ClientRepository clientRepository;

    public Page<TransactionResponse> getAllTransactionsByCompany(CustomUserDetails currentUser,
                                                                 Pageable pageable){
        return transactionRepository.findAllByCompany(currentUser.getUser().getCompany(), pageable)
                .map(TransactionResponse::fromEntity);
    }

    public Page<TransactionResponse> getAllTransactionsByClient(CustomUserDetails currentUser,
                                                                Long clientId,
                                                                Pageable pageable){
        Client client = clientRepository.findByIdAndCompany(clientId ,currentUser.getUser().getCompany())
                .orElseThrow(()-> new ClientNotFoundException("Client with id: " + clientId + "not found"));

        return transactionRepository.findAllByClient(client, pageable)
                .map(TransactionResponse::fromEntity);
    }

    public TransactionResponse getTransactionByClientAndTransactionId(CustomUserDetails currentUser,
                                                                        Long clientId,
                                                                        Long transactionId){
        Client client = clientRepository.findByIdAndCompany(clientId ,currentUser.getUser().getCompany())
                .orElseThrow(()-> new ClientNotFoundException("Client with id: " + clientId + "not found"));

        return transactionRepository.findByClientAndId(client,transactionId)
                .map(TransactionResponse::fromEntity)
                .orElseThrow(()-> new TransactionNotFoundException("Transaction with id: " + transactionId + "not found"));
    }

    public TransactionResponse createTransaction(CustomUserDetails currentUser,
                                                 Long clientId,
                                                 TransactionRequest req){
        Client client = clientRepository.findByIdAndCompany(clientId,currentUser.getUser().getCompany())
                .orElseThrow(()-> new ClientNotFoundException("Client with id: " + clientId + "not found"));

        ValidatePermissionUtil.validatePermission(currentUser.getUser(), client);

        Transaction transaction = new Transaction();
        transaction.setAmount(req.amount());
        transaction.setType(req.type());
        transaction.setStatus(req.status());
        transaction.setDescription(req.description());
        transaction.setClient(client);
        transaction.setCompany(currentUser.getUser().getCompany());
        transaction.setUser(currentUser.getUser());

        Transaction save = transactionRepository.save(transaction);

        return TransactionResponse.fromEntity(save);
    }

    @Transactional
    public TransactionResponse updateTransaction(CustomUserDetails currentUser,
                                                 Long clientId,
                                                 Long transactionId,
                                                 TransactionRequest req){

        Client client = clientRepository.findByIdAndCompany(clientId,currentUser.getUser().getCompany())
                .orElseThrow(()-> new ClientNotFoundException("Client with id: " + clientId + "not found"));

        Transaction transaction = transactionRepository.findByClientAndId(client,transactionId)
                .orElseThrow(()-> new TransactionNotFoundException("Transaction with id: " + transactionId + "not found"));

        ValidatePermissionUtil.validatePermission(currentUser.getUser(), client);

        if(req.amount() != null){
            transaction.setAmount(req.amount());
        }
        if(req.type() != null){
            transaction.setType(req.type());
        }
        if(req.status() != null){
            transaction.setStatus(req.status());
        }
        if(req.description() != null){
            transaction.setDescription(req.description());
        }

        Transaction save = transactionRepository.save(transaction);
        return TransactionResponse.fromEntity(save);
    }

    @Transactional
    public void deleteTransaction(CustomUserDetails currentUser,
                                  Long clientId,
                                  Long transactionId){
        Client client = clientRepository.findByIdAndCompany(clientId,currentUser.getUser().getCompany())
                .orElseThrow(()-> new ClientNotFoundException("Client with id: " + clientId + "not found"));

        Transaction transaction = transactionRepository.findByClientAndId(client,transactionId)
                .orElseThrow(()-> new TransactionNotFoundException("Transaction with id: " + transactionId + "not found"));

        ValidatePermissionUtil.validatePermission(currentUser.getUser(), client);

        transactionRepository.delete(transaction);
    }

    public ClientFinanceSummaryResponse getClientFinanceSummary(
            CustomUserDetails currentUser,
            Long clientId
    ) {
        Long companyId = currentUser.getUser().getCompany().getId();

        if (!clientRepository.existsByIdAndCompanyId(clientId, companyId)) {
            throw new ClientNotFoundException("Client don`t found");
        }

        Optional<Transaction> transaction =
                transactionRepository.findFirstByClientIdAndCompanyIdOrderByCreatedAtDescIdDesc(
                        clientId,
                        companyId
                );

        LastTransaction lastTransaction = transaction
                .map(t -> new LastTransaction(
                        t.getId(),
                        t.getClient().getId(),
                        t.getAmount(),
                        t.getType(),
                        t.getStatus(),
                        t.getCreatedAt()
                ))
                .orElse(null);



        BigDecimal totalIncome =
                transactionRepository.getTotalAmountByClientIdAndCompanyIdAndType(
                        clientId,
                        companyId,
                        TransactionType.INCOME
                );

        BigDecimal totalExpense =
                transactionRepository.getTotalAmountByClientIdAndCompanyIdAndType(
                        clientId,
                        companyId,
                        TransactionType.EXPENSE
                );

        int transactionCount =
                transactionRepository.countByClientIdAndCompanyId(
                        clientId,
                        companyId
                );

        BigDecimal balance = totalIncome.subtract(totalExpense);

        return new ClientFinanceSummaryResponse(
                clientId,
                balance,
                totalIncome,
                totalExpense,
                transactionCount,
                lastTransaction
        );
    }

    public CompanyFinanceSummaryResponse getCompanyFinanceSummary(
            CustomUserDetails currentUser
    ) {
        Long companyId = currentUser.getUser().getCompany().getId();

        Transaction transaction = transactionRepository.findFirstByCompanyIdOrderByCreatedAtDescIdDesc(companyId)
                .orElseThrow(() -> new TransactionNotFoundException("Company don`t have any transaction"));

        LastTransaction lastTransaction = new LastTransaction(
                transaction.getId(),
                transaction.getClient().getId(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getStatus(),
                transaction.getCreatedAt()
        );

        BigDecimal totalIncome =
                transactionRepository.getTotalAmountByCompanyIdAndType(
                        companyId,
                        TransactionType.INCOME
                );

        BigDecimal totalExpense =
                transactionRepository.getTotalAmountByCompanyIdAndType(
                        companyId,
                        TransactionType.EXPENSE
                );

        BigDecimal totalBalance = totalIncome.subtract(totalExpense);

        int transactionCount =
                transactionRepository.countByCompanyId(
                        companyId
                );

        int clientCount = clientRepository.countByCompanyId(companyId);

        BigDecimal averageTransactionAmount = transactionRepository.getAverageTransactionAmount(companyId);


        return new CompanyFinanceSummaryResponse(
                totalBalance,
                totalIncome,
                totalExpense,
                transactionCount,
                clientCount,
                averageTransactionAmount,
                lastTransaction
        );
    }

}
