package ru.slivki.financial_doctor.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.slivki.financial_doctor.bank.model.BankTransaction;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface BankTransactionRepository extends JpaRepository<BankTransaction, String> {

    List<BankTransaction> findByUserId(Long userId);

    List<BankTransaction> findByAccountIdAndUserId(String accountId, Long userId);

    @Query("SELECT t FROM BankTransaction t WHERE t.accountId = :accountId " +
           "AND t.userId = :userId " +
           "AND t.transactionDateTime >= :from AND t.transactionDateTime < :to")
    List<BankTransaction> findByAccountIdAndUserIdAndTransactionDateTimeBetween(
            @Param("accountId") String accountId,
            @Param("userId") Long userId,
            @Param("from") OffsetDateTime from,
            @Param("to") OffsetDateTime to);
}



