package com.maloy.iremember.controllers.finance;

import com.maloy.iremember.dto.finance.ClientFinanceSummaryResponse;
import com.maloy.iremember.dto.finance.CompanyFinanceSummaryResponse;
import com.maloy.iremember.dto.finance.TransactionRequest;
import com.maloy.iremember.dto.finance.TransactionResponse;
import com.maloy.iremember.security.CustomUserDetails;
import com.maloy.iremember.services.finance.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/finance")
public class FinanceController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<Page<TransactionResponse>> getTransactions(
            @AuthenticationPrincipal CustomUserDetails currentUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ){
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(transactionService.getAllTransactionsByCompany(currentUser, pageable));
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<Page<TransactionResponse>> getTransactionsByClientId(
            @PathVariable Long clientId,
            @AuthenticationPrincipal CustomUserDetails currentUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ){
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(transactionService.getAllTransactionsByClient(currentUser,clientId,pageable));
    }

    @PostMapping("/{clientId}")
    public ResponseEntity<TransactionResponse> createTransaction(
            @AuthenticationPrincipal CustomUserDetails currentUser,
            @PathVariable Long clientId,
            @RequestBody TransactionRequest req
    ){
        return ResponseEntity.ok(transactionService.createTransaction(currentUser,clientId,req));
    }

    @PutMapping("/{clientId}/transaction/{transactionId}")
    public ResponseEntity<TransactionResponse> updateTransaction(
            @AuthenticationPrincipal CustomUserDetails currentUser,
            @PathVariable Long clientId,
            @PathVariable Long transactionId,
            @RequestBody TransactionRequest req
    ){
        return ResponseEntity.ok(transactionService.updateTransaction(currentUser,clientId,transactionId,req));
    }

    @DeleteMapping("/{clientId}/transaction/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(
            @AuthenticationPrincipal CustomUserDetails currentUser,
            @PathVariable Long clientId,
            @PathVariable Long transactionId
    ){
        transactionService.deleteTransaction(currentUser,clientId,transactionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stats")
    public ResponseEntity<CompanyFinanceSummaryResponse> getFinanceStatsByCompany(
            @AuthenticationPrincipal CustomUserDetails currentUser
    ){
        return ResponseEntity.ok(transactionService.getCompanyFinanceSummary(currentUser));
    }

    @GetMapping("/stats/{clientId}")
    public ResponseEntity<ClientFinanceSummaryResponse> getFinanceStatsByClient(
            @AuthenticationPrincipal CustomUserDetails currentUser,
            @PathVariable Long clientId
    ){
        return ResponseEntity.ok(transactionService.getClientFinanceSummary(currentUser, clientId));
    }

    @GetMapping("/summary")
    public ResponseEntity<CompanyFinanceSummaryResponse> getCompanyFinanceSummary(
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        return ResponseEntity.ok(
                transactionService.getCompanyFinanceSummary(currentUser)
        );
    }

    @GetMapping("/{clientId}/summary")
    public ResponseEntity<ClientFinanceSummaryResponse> getClientFinanceSummary(
            @AuthenticationPrincipal CustomUserDetails currentUser,
            @PathVariable Long clientId
    ) {
        return ResponseEntity.ok(
                transactionService.getClientFinanceSummary(
                        currentUser,
                        clientId
                )
        );
    }
}
