package ru.slivki.financial_doctor.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.slivki.financial_doctor.bank.model.BankStatement;

@Repository
public interface BankStatementRepository extends JpaRepository<BankStatement, String> {
}


