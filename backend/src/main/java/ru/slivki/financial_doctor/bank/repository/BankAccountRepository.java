package ru.slivki.financial_doctor.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.slivki.financial_doctor.bank.model.BankAccount;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

    List<BankAccount> findByUserId(Long userId);

    Optional<BankAccount> findByAccountIdAndUserId(String accountId, Long userId);
}



