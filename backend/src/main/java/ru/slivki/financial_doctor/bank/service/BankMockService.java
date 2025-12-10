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

    public ConsentResponse createConsent(CreateConsentRequest request) {
        BankConsent consent = new BankConsent();
        consent.setConsentId(UUID.randomUUID().toString());
        if (request.getData() != null) {
            consent.setPermissions(request.getData().getPermissions());
            consent.setExpirationDateTime(parseDate(request.getData().getExpirationDateTime()));
            consent.setTransactionFromDateTime(parseDate(request.getData().getTransactionFromDateTime()));
            consent.setTransactionToDateTime(parseDate(request.getData().getTransactionToDateTime()));
        }
        consentRepository.save(consent);
        return new ConsentResponse(new ConsentResponse.Data(consent.getConsentId(), consent.getStatus()));
    }

    public ConsentResponse getConsent(String consentId) {
        BankConsent consent = consentRepository.findById(consentId).orElseGet(() -> {
            BankConsent c = new BankConsent();
            c.setConsentId(consentId);
            return c;
        });
        return new ConsentResponse(new ConsentResponse.Data(consent.getConsentId(), consent.getStatus()));
    }

    public ConsentResponse getRetrievalGrant(String consentId) {
        return getConsent(consentId);
    }

    public void deleteConsent(String consentId) {
        consentRepository.deleteById(consentId);
    }

    public AccountListResponse getAccounts() {
        List<AccountListResponse.Account> accounts = accountRepository.findAll().stream()
                .map(this::toAccountDto)
                .toList();
        return new AccountListResponse(new AccountListResponse.Data(accounts));
    }

    public AccountListResponse getAccount(String accountId) {
        AccountListResponse.Account account = accountRepository.findById(accountId)
                .map(this::toAccountDto)
                .orElseGet(() -> new AccountListResponse.Account(accountId, "Unknown", null, "RUB", BigDecimal.ZERO));
        return new AccountListResponse(new AccountListResponse.Data(List.of(account)));
    }

    public BalanceListResponse getBalances() {
        List<BalanceListResponse.Balance> balances = accountRepository.findAll().stream()
                .map(this::toBalanceDto)
                .toList();
        return new BalanceListResponse(new BalanceListResponse.Data(balances));
    }

    public BalanceListResponse getBalancesByAccount(String accountId) {
        List<BalanceListResponse.Balance> balances = accountRepository.findById(accountId)
                .map(List::of)
                .orElse(List.of())
                .stream()
                .map(this::toBalanceDto)
                .toList();
        return new BalanceListResponse(new BalanceListResponse.Data(balances));
    }

    public TransactionListResponse getTransactions() {
        List<TransactionListResponse.Transaction> txns = transactionRepository.findAll().stream()
                .map(this::toTransactionDto)
                .toList();
        return new TransactionListResponse(new TransactionListResponse.Data(txns));
    }

    public TransactionListResponse getTransactionsByAccount(String accountId, String fromBookingDateTime, String toBookingDateTime) {
        List<BankTransaction> bankTransactions;
        
        if (fromBookingDateTime != null && toBookingDateTime != null) {
            OffsetDateTime from = parseDate(fromBookingDateTime);
            OffsetDateTime to = parseDate(toBookingDateTime);
            bankTransactions = transactionRepository.findByAccountIdAndTransactionDateTimeBetween(accountId, from, to);
        } else {
            bankTransactions = transactionRepository.findByAccountId(accountId);
        }
        
        List<TransactionListResponse.Transaction> txns = bankTransactions.stream()
                .map(this::toTransactionDto)
                .toList();
        return new TransactionListResponse(new TransactionListResponse.Data(txns));
    }

    public StatementCreateResponse createStatement(CreateStatementRequest request) {
        StatementCreateResponse.Statement dto = new StatementCreateResponse.Statement(UUID.randomUUID().toString());
        BankStatement statement = new BankStatement();
        statement.setStatementId(dto.getStatementId());
        if (request.getData() != null && request.getData().getStatement() != null) {
            statement.setAccountId(request.getData().getStatement().getAccountId());
            statement.setFromBookingDateTime(parseDate(request.getData().getStatement().getFromBookingDateTime()));
            statement.setToBookingDateTime(parseDate(request.getData().getStatement().getToBookingDateTime()));
        } else {
            statement.setAccountId("100201");
        }
        statementRepository.save(statement);
        return new StatementCreateResponse(new StatementCreateResponse.Data(dto));
    }

    public StatementListResponse getStatements() {
        List<StatementListResponse.Statement> statements = statementRepository.findAll().stream()
                .map(this::toStatementDto)
                .toList();
        return new StatementListResponse(new StatementListResponse.Data(statements));
    }

    public StatementListResponse getStatement(String statementId) {
        StatementListResponse.Statement statement = statementRepository.findById(statementId)
                .map(this::toStatementDto)
                .orElseGet(() -> new StatementListResponse.Statement(statementId, "100201", null, null));
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

}


