package com.maloy.iremember.dto.finance;

import java.math.BigDecimal;

public record CompanyFinanceSummaryResponse(
        BigDecimal totalBalance,
        BigDecimal totalIncome,
        BigDecimal totalExpense,
        int transactionCount,
        int clientCount,
        BigDecimal averageTransactionAmount,

        LastTransaction lastTransaction
) {
}
