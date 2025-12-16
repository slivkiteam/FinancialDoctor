package ru.slivki.financial_doctor.bank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.slivki.financial_doctor.bank.model.BankTransaction;
import ru.slivki.financial_doctor.bank.repository.BankTransactionRepository;
import ru.slivki.financial_doctor.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Adapter to read mock-bank data and map it to app-level Transaction objects
 * (not persisted) so financial diagnosis can work over the bank DB.
 */
@Service
@RequiredArgsConstructor
public class BankDataProvider {

    private final BankTransactionRepository bankTransactionRepository;

    /**
     * Returns transactions mapped from bank DB into in-memory Transaction objects for the given user.
     */
    public List<Transaction> loadTransactionsFromBank(Long userId) {
        return bankTransactionRepository.findByUserId(userId)
                .stream()
                .map(this::toTransaction)
                .toList();
    }

    private Transaction toTransaction(BankTransaction bankTxn) {
        Transaction t = new Transaction();
        // Debit -> negative amount, Credit -> positive
        BigDecimal amount = bankTxn.getAmount() == null ? BigDecimal.ZERO : bankTxn.getAmount();
        if ("DEBIT".equalsIgnoreCase(bankTxn.getCreditDebitIndicator()) || "DEBIT".equalsIgnoreCase(bankTxn.getTransactionInformation())) {
            amount = amount.negate();
        }
        t.setAmount(amount);
        t.setCurrency(bankTxn.getCurrency());
        t.setTransactionType(bankTxn.getCreditDebitIndicator());
        t.setDescription(bankTxn.getTransactionInformation());
        t.setMerchantName(bankTxn.getMerchantName());
        t.setTransactionDate(
                bankTxn.getTransactionDateTime() != null
                        ? bankTxn.getTransactionDateTime().toLocalDateTime()
                        : LocalDateTime.now());
        return t;
    }
}



