package ru.slivki.financial_doctor.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.slivki.financial_doctor.bank.model.BankTransaction;

import java.util.List;

@Repository
public interface BankTransactionRepository extends JpaRepository<BankTransaction, String> {

    List<BankTransaction> findByAccountId(String accountId);
}


