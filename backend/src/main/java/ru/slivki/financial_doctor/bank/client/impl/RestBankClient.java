package ru.slivki.financial_doctor.bank.client.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import ru.slivki.financial_doctor.bank.client.BankClient;
import ru.slivki.financial_doctor.bank.dto.TransactionListResponse;
import ru.slivki.financial_doctor.bank.dto.AccountListResponse;
import ru.slivki.financial_doctor.model.Account;
import ru.slivki.financial_doctor.model.Transaction;
import ru.slivki.financial_doctor.repository.AccountRepository;
import ru.slivki.financial_doctor.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST client implementation that calls bank service via HTTP.
 * Maps bank DTOs to application domain models.
 */
@Slf4j
@Component
public class RestBankClient implements BankClient {

    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    private final String bankServiceUrl;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final RestClient restClient;

    public RestBankClient(AccountRepository accountRepository,
                         UserRepository userRepository,
                         @Value("${bank.service.url:http://localhost:8081}") String bankServiceUrl) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.bankServiceUrl = bankServiceUrl;
        this.restClient = RestClient.builder()
                .baseUrl(bankServiceUrl)
                .build();
    }

    @Override
    public List<Transaction> getTransactions(Long userId, LocalDateTime from, LocalDateTime to) {
        log.debug("Fetching transactions for userId={}, from={}, to={}", userId, from, to);

        try {
            // Get user's accounts from our database; if none, try to sync from bank
            List<Account> userAccounts = accountRepository.findByUser_Id(userId);
            if (userAccounts.isEmpty()) {
                log.info("No local accounts for userId={}, loading from mock bank", userId);
                userAccounts = syncAccountsFromBank(userId);
                if (userAccounts.isEmpty()) {
                    log.warn("No accounts found for userId={} even after sync from bank", userId);
                    return List.of();
                }
            }

            // Fetch transactions for each account from bank
            List<Transaction> allTransactions = new ArrayList<>();
            for (Account account : userAccounts) {
                String accountId = account.getExternalAccountId();
                try {
                    // Convert LocalDateTime to OffsetDateTime for bank API
                    OffsetDateTime fromOffset = from.atOffset(ZoneOffset.UTC);
                    OffsetDateTime toOffset = to.atOffset(ZoneOffset.UTC);
                    TransactionListResponse response = restClient.get()
                            .uri("/accounts/{accountId}/transactions?userId={userId}&fromBookingDateTime={from}&toBookingDateTime={to}",
                                    accountId,
                                    userId,
                                    fromOffset.format(ISO_FORMATTER),
                                    toOffset.format(ISO_FORMATTER))
                            .retrieve()
                            .body(TransactionListResponse.class);

                    if (response != null && response.getData() != null && response.getData().getTransaction() != null) {
                        List<Transaction> accountTransactions = response.getData().getTransaction().stream()
                                .map(bankTxn -> mapToTransaction(bankTxn, account, userId))
                                .collect(Collectors.toList());
                        allTransactions.addAll(accountTransactions);
                    }
                } catch (RestClientException e) {
                    log.error("Error fetching transactions for accountId={}, userId={}", accountId, userId, e);
                    // Continue with other accounts
                }
            }

            log.debug("Fetched {} transactions for userId={}", allTransactions.size(), userId);
            return allTransactions;

        } catch (Exception e) {
            log.error("Error fetching transactions for userId={}", userId, e);
            return List.of();
        }
    }

    /**
     * Loads accounts for user from mock bank and persists them into primary DB.
     */
    private List<Account> syncAccountsFromBank(Long userId) {
        try {
            AccountListResponse response = restClient.get()
                    .uri("/accounts?userId={userId}", userId)
                    .retrieve()
                    .body(AccountListResponse.class);

            if (response == null || response.getData() == null || response.getData().getAccount() == null) {
                return List.of();
            }

            var user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                log.warn("User not found when syncing accounts from bank, userId={}", userId);
                return List.of();
            }

            List<Account> accounts = response.getData().getAccount().stream()
                    .map(a -> {
                        Account acc = new Account();
                        acc.setUser(user);
                        acc.setExternalAccountId(a.getAccountId());
                        acc.setAccountType(a.getAccountType());
                        acc.setAccountName(a.getAccountName());
                        acc.setCurrency(a.getCurrency());
                        acc.setBalance(a.getBalance());
                        acc.setIsActive(true);
                        return acc;
                    })
                    .toList();

            return accountRepository.saveAll(accounts);
        } catch (Exception e) {
            log.error("Error syncing accounts from mock bank for userId={}", userId, e);
            return List.of();
        }
    }

    private Transaction mapToTransaction(TransactionListResponse.Transaction bankTxn,
                                         Account account,
                                         Long userId) {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        // Ensure user is set from account
        if (account.getUser() != null) {
            transaction.setUser(account.getUser());
        } else {
            log.warn("Account {} has no user set", account.getId());
        }
        transaction.setExternalTransactionId(bankTxn.getTransactionId());

        // Parse transaction date
        if (bankTxn.getTransactionDateTime() != null) {
            try {
                transaction.setTransactionDate(LocalDateTime.parse(bankTxn.getTransactionDateTime(), ISO_FORMATTER));
                transaction.setPostingDate(transaction.getTransactionDate().toLocalDate());
            } catch (Exception e) {
                log.warn("Failed to parse transaction date: {}", bankTxn.getTransactionDateTime(), e);
                transaction.setTransactionDate(LocalDateTime.now());
            }
        } else {
            transaction.setTransactionDate(LocalDateTime.now());
        }

        // Parse amount
        BigDecimal amount = BigDecimal.ZERO;
        if (bankTxn.getAmount() != null && bankTxn.getAmount().getAmount() != null) {
            try {
                amount = new BigDecimal(bankTxn.getAmount().getAmount());
            } catch (NumberFormatException e) {
                log.warn("Failed to parse amount: {}", bankTxn.getAmount().getAmount(), e);
            }
        }

        // Apply credit/debit indicator
        if ("DEBIT".equalsIgnoreCase(bankTxn.getCreditDebitIndicator())) {
            amount = amount.negate();
        }

        transaction.setAmount(amount);
        transaction.setCurrency(bankTxn.getAmount() != null && bankTxn.getAmount().getCurrency() != null
                ? bankTxn.getAmount().getCurrency()
                : "RUB");

        // Map transaction type
        String indicator = bankTxn.getCreditDebitIndicator();
        if ("DEBIT".equalsIgnoreCase(indicator)) {
            String desc = bankTxn.getTransactionInformation() != null
                    ? bankTxn.getTransactionInformation().toLowerCase()
                    : "";
            if (desc.contains("кредит") || desc.contains("loan")) {
                transaction.setTransactionType("credit_payment");
            } else if (desc.contains("atm") || desc.contains("налич")) {
                transaction.setTransactionType("cash");
            } else if (desc.contains("инвест") || desc.contains("invest") || desc.contains("crypto")) {
                transaction.setTransactionType("investment");
            } else {
                transaction.setTransactionType("expense");
            }
        } else {
            transaction.setTransactionType("income");
        }

        transaction.setMerchantName(bankTxn.getMerchantName());
        transaction.setDescription(bankTxn.getTransactionInformation());

        // Try to infer merchant category from description
        if (bankTxn.getTransactionInformation() != null) {
            String desc = bankTxn.getTransactionInformation().toLowerCase();
            if (desc.contains("кредит") || desc.contains("loan")) {
                transaction.setMerchantCategory("loan");
            } else if (desc.contains("atm") || desc.contains("налич")) {
                transaction.setMerchantCategory("atm");
            } else if (desc.contains("инвест") || desc.contains("invest") || desc.contains("crypto")) {
                transaction.setMerchantCategory("invest");
            } else if (desc.contains("зарплат") || desc.contains("salary")) {
                transaction.setMerchantCategory("salary");
            } else {
                transaction.setMerchantCategory("other");
            }
        }

        return transaction;
    }
}

