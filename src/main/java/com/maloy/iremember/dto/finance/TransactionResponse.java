package com.maloy.iremember.dto.finance;

import com.maloy.iremember.entity.finance.Transaction;
import com.maloy.iremember.enums.finance.TransactionStatus;
import com.maloy.iremember.enums.finance.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        Long transactionId,
        Long clientId,
        String clientFName,
        String clientLName,
        BigDecimal amount,
        TransactionType type,
        TransactionStatus status,
        String description,
        Long createdByUserId,
        String createdByUsername,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static TransactionResponse fromEntity(Transaction transaction){
        return new TransactionResponse(
                transaction.getId(),
                transaction.getClient().getId(),
                transaction.getClient().getFirstName(),
                transaction.getClient().getLastName(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getStatus(),
                transaction.getDescription(),
                transaction.getUser().getId(),
                transaction.getUser().getUsername(),
                transaction.getCreatedAt(),
                transaction.getUpdatedAt());
    }
}
