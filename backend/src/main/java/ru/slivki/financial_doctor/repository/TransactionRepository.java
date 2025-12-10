package ru.slivki.financial_doctor.repository;

import ru.slivki.financial_doctor.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserId(Long userId);

    List<Transaction> findByAccountId(Long accountId);

    List<Transaction> findByUserIdAndTransactionDateBetween(Long userId,
                                                            java.time.LocalDateTime start,
                                                            java.time.LocalDateTime end);

    Optional<Transaction> findByAccountIdAndExternalTransactionId(Long accountId, String externalTxnId);
}
