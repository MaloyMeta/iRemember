package com.maloy.iremember.dto.finance;

import com.maloy.iremember.enums.finance.TransactionStatus;
import com.maloy.iremember.enums.finance.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LastTransaction(
        Long transactionId,
        Long clientId,
        BigDecimal amount,
        TransactionType type,
        TransactionStatus status,
        LocalDateTime createdAt
) {
}
