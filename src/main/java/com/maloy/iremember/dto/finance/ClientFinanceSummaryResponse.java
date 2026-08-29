package com.maloy.iremember.dto.finance;

import java.math.BigDecimal;

public record ClientFinanceSummaryResponse(
        Long clientId,
        BigDecimal balance,
        BigDecimal totalIncome,
        BigDecimal totalExpense,
        int transactionCount,

        LastTransaction lastTransaction
) {
}
