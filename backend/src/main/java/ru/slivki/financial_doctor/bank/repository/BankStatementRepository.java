package ru.slivki.financial_doctor.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.slivki.financial_doctor.bank.model.BankStatement;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankStatementRepository extends JpaRepository<BankStatement, String> {

    List<BankStatement> findByUserId(Long userId);

    Optional<BankStatement> findByStatementIdAndUserId(String statementId, Long userId);
}



