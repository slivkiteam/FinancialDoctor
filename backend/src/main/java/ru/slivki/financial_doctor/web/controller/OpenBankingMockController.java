package ru.slivki.financial_doctor.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.slivki.financial_doctor.bank.dto.AccountListResponse;
import ru.slivki.financial_doctor.bank.dto.BalanceListResponse;
import ru.slivki.financial_doctor.bank.dto.ConsentResponse;
import ru.slivki.financial_doctor.bank.dto.CreateConsentRequest;
import ru.slivki.financial_doctor.bank.dto.CreateStatementRequest;
import ru.slivki.financial_doctor.bank.dto.StatementCreateResponse;
import ru.slivki.financial_doctor.bank.dto.StatementListResponse;
import ru.slivki.financial_doctor.bank.dto.TransactionListResponse;
import ru.slivki.financial_doctor.bank.service.BankMockService;
import ru.slivki.financial_doctor.web.security.JwtEntity;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Mock bank implementation backed by its own PostgreSQL database.
 * Endpoints match the Accounts Sandbox v1.3 Postman collection.
 */
@RestController
@RequestMapping
@RequiredArgsConstructor
public class OpenBankingMockController {

    private final BankMockService bankMockService;

    @PostMapping(value = "/connect/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Map<String, Object> token(@RequestParam Map<String, String> params) {
        String grantType = params.getOrDefault("grant_type", "client_credentials");
        Map<String, Object> response = new HashMap<>();
        response.put("access_token", UUID.randomUUID().toString());
        response.put("token_type", "Bearer");
        response.put("expires_in", 3600);
        response.put("scope", params.getOrDefault("scope", "accounts"));

        if ("authorization_code".equals(grantType) || "refresh_token".equals(grantType)) {
            response.put("refresh_token", UUID.randomUUID().toString());
            response.put("id_token", UUID.randomUUID().toString());
        }

        return response;
    }

    @PostMapping(value = "/connect/introspect", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Map<String, Object> introspect(@RequestParam Map<String, String> params) {
        Map<String, Object> response = new HashMap<>();
        response.put("active", true);
        response.put("token", params.getOrDefault("token", ""));
        response.put("token_type", "access_token");
        response.put("scope", "accounts");
        long now = Instant.now().getEpochSecond();
        response.put("iat", now);
        response.put("exp", now + 3600);
        response.put("sub", "demo-user");
        response.put("aud", params.getOrDefault("client_id", "demo-client"));
        return response;
    }

    @PostMapping(value = "/account-consents", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ConsentResponse createConsent(@RequestParam("userId") Long userId,
                                         @RequestBody CreateConsentRequest request) {
        return bankMockService.createConsent(request, userId);
    }

    @GetMapping("/account-consents/{consentId}")
    public ConsentResponse getConsent(@RequestParam("userId") Long userId,
                                      @PathVariable String consentId) {
        return bankMockService.getConsent(consentId, userId);
    }

    @GetMapping("/account-consents/{consentId}/retrieval-grant")
    public ConsentResponse getRetrievalGrant(@RequestParam("userId") Long userId,
                                             @PathVariable String consentId) {
        return bankMockService.getRetrievalGrant(consentId, userId);
    }

    @DeleteMapping("/account-consents/{consentId}")
    public ResponseEntity<Void> deleteConsent(@RequestParam("userId") Long userId,
                                              @PathVariable String consentId) {
        bankMockService.deleteConsent(consentId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/accounts")
    public AccountListResponse getAccounts(@RequestParam("userId") Long userId) {
        return bankMockService.getAccounts(userId);
    }

    @GetMapping("/accounts/{accountId}")
    public AccountListResponse getAccountById(@RequestParam("userId") Long userId,
                                              @PathVariable String accountId) {
        return bankMockService.getAccount(accountId, userId);
    }

    @GetMapping("/balances")
    public BalanceListResponse getBalances(@RequestParam("userId") Long userId) {
        return bankMockService.getBalances(userId);
    }

    @GetMapping("/accounts/{accountId}/balances")
    public BalanceListResponse getBalancesByAccount(@RequestParam("userId") Long userId,
                                                    @PathVariable String accountId) {
        return bankMockService.getBalancesByAccount(accountId, userId);
    }

    @GetMapping("/transactions")
    public TransactionListResponse getTransactions(@RequestParam("userId") Long userId) {
        return bankMockService.getTransactions(userId);
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public TransactionListResponse getTransactionsByAccount(
            @RequestParam("userId") Long userId,
            @PathVariable String accountId,
            @RequestParam(required = false) String fromBookingDateTime,
            @RequestParam(required = false) String toBookingDateTime) {
        return bankMockService.getTransactionsByAccount(accountId, fromBookingDateTime, toBookingDateTime, userId);
    }

    @PostMapping(value = "/statements", consumes = MediaType.APPLICATION_JSON_VALUE)
    public StatementCreateResponse createStatement(@RequestParam("userId") Long userId,
                                                   @RequestBody CreateStatementRequest request) {
        return bankMockService.createStatement(request, userId);
    }

    @GetMapping("/statements")
    public StatementListResponse getStatements(@RequestParam("userId") Long userId) {
        return bankMockService.getStatements(userId);
    }

    @GetMapping("/statements/{statementId}")
    public StatementListResponse getStatementById(@RequestParam("userId") Long userId,
                                                  @PathVariable String statementId) {
        return bankMockService.getStatement(statementId, userId);
    }
}


