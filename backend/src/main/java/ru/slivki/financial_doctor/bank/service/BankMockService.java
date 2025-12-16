package ru.slivki.financial_doctor.bank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.slivki.financial_doctor.bank.dto.AccountListResponse;
import ru.slivki.financial_doctor.bank.dto.BalanceListResponse;
import ru.slivki.financial_doctor.bank.dto.ConsentResponse;
import ru.slivki.financial_doctor.bank.dto.CreateConsentRequest;
import ru.slivki.financial_doctor.bank.dto.CreateStatementRequest;
import ru.slivki.financial_doctor.bank.dto.StatementCreateResponse;
import ru.slivki.financial_doctor.bank.dto.StatementListResponse;
import ru.slivki.financial_doctor.bank.dto.TransactionListResponse;
import ru.slivki.financial_doctor.bank.model.BankAccount;
import ru.slivki.financial_doctor.bank.model.BankConsent;
import ru.slivki.financial_doctor.bank.model.BankStatement;
import ru.slivki.financial_doctor.bank.model.BankTransaction;
import ru.slivki.financial_doctor.bank.repository.BankAccountRepository;
import ru.slivki.financial_doctor.bank.repository.BankConsentRepository;
import ru.slivki.financial_doctor.bank.repository.BankStatementRepository;
import ru.slivki.financial_doctor.bank.repository.BankTransactionRepository;
import ru.slivki.financial_doctor.exception.ResourceNotFoundException;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(transactionManager = "bankTransactionManager")
public class BankMockService {

    private final BankAccountRepository accountRepository;
    private final BankConsentRepository consentRepository;
    private final BankStatementRepository statementRepository;
    private final BankTransactionRepository transactionRepository;

    public ConsentResponse createConsent(CreateConsentRequest request, Long userId) {
        BankConsent consent = new BankConsent();
        consent.setConsentId(UUID.randomUUID().toString());
        consent.setUserId(userId);
        if (request.getData() != null) {
            consent.setPermissions(request.getData().getPermissions());
            consent.setExpirationDateTime(parseDate(request.getData().getExpirationDateTime()));
            consent.setTransactionFromDateTime(parseDate(request.getData().getTransactionFromDateTime()));
            consent.setTransactionToDateTime(parseDate(request.getData().getTransactionToDateTime()));
        }
        consentRepository.save(consent);
        return new ConsentResponse(new ConsentResponse.Data(consent.getConsentId(), consent.getStatus()));
    }

    public ConsentResponse getConsent(String consentId, Long userId) {
        BankConsent consent = consentRepository.findByConsentIdAndUserId(consentId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Consent not found for user"));
        return new ConsentResponse(new ConsentResponse.Data(consent.getConsentId(), consent.getStatus()));
    }

    public ConsentResponse getRetrievalGrant(String consentId, Long userId) {
        return getConsent(consentId, userId);
    }

    public void deleteConsent(String consentId, Long userId) {
        BankConsent consent = consentRepository.findByConsentIdAndUserId(consentId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Consent not found for user"));
        consentRepository.delete(consent);
    }

    public AccountListResponse getAccounts(Long userId) {
        List<AccountListResponse.Account> accounts = accountRepository.findByUserId(userId).stream()
                .map(this::toAccountDto)
                .toList();
        return new AccountListResponse(new AccountListResponse.Data(accounts));
    }

    public AccountListResponse getAccount(String accountId, Long userId) {
        AccountListResponse.Account account = accountRepository.findByAccountIdAndUserId(accountId, userId)
                .map(this::toAccountDto)
                .orElseGet(() -> new AccountListResponse.Account(accountId, "Unknown", null, "RUB", BigDecimal.ZERO));
        return new AccountListResponse(new AccountListResponse.Data(List.of(account)));
    }

    public BalanceListResponse getBalances(Long userId) {
        List<BalanceListResponse.Balance> balances = accountRepository.findByUserId(userId).stream()
                .map(this::toBalanceDto)
                .toList();
        return new BalanceListResponse(new BalanceListResponse.Data(balances));
    }

    public BalanceListResponse getBalancesByAccount(String accountId, Long userId) {
        ensureAccountBelongsToUser(accountId, userId);
        List<BalanceListResponse.Balance> balances = accountRepository.findByAccountIdAndUserId(accountId, userId)
                .map(List::of)
                .orElse(List.of())
                .stream()
                .map(this::toBalanceDto)
                .toList();
        return new BalanceListResponse(new BalanceListResponse.Data(balances));
    }

    public TransactionListResponse getTransactions(Long userId) {
        List<TransactionListResponse.Transaction> txns = transactionRepository.findByUserId(userId).stream()
                .map(this::toTransactionDto)
                .toList();
        return new TransactionListResponse(new TransactionListResponse.Data(txns));
    }

    public TransactionListResponse getTransactionsByAccount(String accountId, String fromBookingDateTime, String toBookingDateTime, Long userId) {
        ensureAccountBelongsToUser(accountId, userId);
        List<BankTransaction> bankTransactions;
        
        if (fromBookingDateTime != null && toBookingDateTime != null) {
            OffsetDateTime from = parseDate(fromBookingDateTime);
            OffsetDateTime to = parseDate(toBookingDateTime);
            bankTransactions = transactionRepository.findByAccountIdAndUserIdAndTransactionDateTimeBetween(accountId, userId, from, to);
        } else {
            bankTransactions = transactionRepository.findByAccountIdAndUserId(accountId, userId);
        }
        
        List<TransactionListResponse.Transaction> txns = bankTransactions.stream()
                .map(this::toTransactionDto)
                .toList();
        return new TransactionListResponse(new TransactionListResponse.Data(txns));
    }

    public StatementCreateResponse createStatement(CreateStatementRequest request, Long userId) {
        StatementCreateResponse.Statement dto = new StatementCreateResponse.Statement(UUID.randomUUID().toString());
        BankStatement statement = new BankStatement();
        statement.setStatementId(dto.getStatementId());
        statement.setUserId(userId);
        if (request.getData() != null && request.getData().getStatement() != null) {
            statement.setAccountId(request.getData().getStatement().getAccountId());
            statement.setFromBookingDateTime(parseDate(request.getData().getStatement().getFromBookingDateTime()));
            statement.setToBookingDateTime(parseDate(request.getData().getStatement().getToBookingDateTime()));
        } else {
            throw new ResourceNotFoundException("Statement requires an accountId");
        }
        ensureAccountBelongsToUser(statement.getAccountId(), userId);
        statementRepository.save(statement);
        return new StatementCreateResponse(new StatementCreateResponse.Data(dto));
    }

    public StatementListResponse getStatements(Long userId) {
        List<StatementListResponse.Statement> statements = statementRepository.findByUserId(userId).stream()
                .map(this::toStatementDto)
                .toList();
        return new StatementListResponse(new StatementListResponse.Data(statements));
    }

    public StatementListResponse getStatement(String statementId, Long userId) {
        StatementListResponse.Statement statement = statementRepository.findByStatementIdAndUserId(statementId, userId)
                .map(this::toStatementDto)
                .orElseGet(() -> new StatementListResponse.Statement(statementId, null, null, null));
        return new StatementListResponse(new StatementListResponse.Data(List.of(statement)));
    }

    private AccountListResponse.Account toAccountDto(BankAccount a) {
        return new AccountListResponse.Account(
                a.getAccountId(),
                a.getAccountType(),
                a.getAccountName(),
                a.getCurrency(),
                a.getBalance()
        );
    }

    private BalanceListResponse.Balance toBalanceDto(BankAccount a) {
        return new BalanceListResponse.Balance(
                a.getAccountId(),
                new BalanceListResponse.Amount(
                        a.getBalance() != null ? a.getBalance().toPlainString() : "0.00",
                        a.getCurrency()
                )
        );
    }

    private TransactionListResponse.Transaction toTransactionDto(BankTransaction txn) {
        return new TransactionListResponse.Transaction(
                txn.getAccountId(),
                txn.getTransactionId(),
                txn.getTransactionDateTime().toString(),
                new TransactionListResponse.Amount(
                        txn.getAmount().toPlainString(),
                        txn.getCurrency()
                ),
                txn.getCreditDebitIndicator(),
                txn.getMerchantName(),
                txn.getTransactionInformation()
        );
    }

    private StatementListResponse.Statement toStatementDto(BankStatement s) {
        return new StatementListResponse.Statement(
                s.getStatementId(),
                s.getAccountId(),
                s.getFromBookingDateTime() != null ? s.getFromBookingDateTime().toString() : null,
                s.getToBookingDateTime() != null ? s.getToBookingDateTime().toString() : null
        );
    }

    private OffsetDateTime parseDate(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return OffsetDateTime.parse(value);
    }

    private void ensureAccountBelongsToUser(String accountId, Long userId) {
        accountRepository.findByAccountIdAndUserId(accountId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for user"));
    }

}


