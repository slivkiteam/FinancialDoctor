package ru.slivki.financial_doctor.bank.client;

import ru.slivki.financial_doctor.bank.dto.TransactionListResponse;
import ru.slivki.financial_doctor.model.Transaction;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST client for communicating with bank service.
 * This abstraction allows switching between mock bank and real bank without changing business logic.
 */
public interface BankClient {

    /**
     * Fetches transactions for a user within the specified time period from the bank.
     *
     * @param userId user identifier
     * @param from   start of the period (inclusive)
     * @param to     end of the period (exclusive)
     * @return list of transactions mapped to application model
     */
    List<Transaction> getTransactions(Long userId, LocalDateTime from, LocalDateTime to);
}


