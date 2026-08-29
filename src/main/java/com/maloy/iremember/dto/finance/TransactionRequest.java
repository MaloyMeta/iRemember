package com.maloy.iremember.dto.finance;

import com.maloy.iremember.enums.finance.TransactionStatus;
import com.maloy.iremember.enums.finance.TransactionType;

import java.math.BigDecimal;

public record TransactionRequest(
        BigDecimal amount,
        TransactionType type,
        TransactionStatus status,
        String description
) {
}
