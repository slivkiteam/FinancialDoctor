package ru.slivki.financial_doctor.repository;

import ru.slivki.financial_doctor.model.FinancialDiagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialDiagnosisRepository extends JpaRepository<FinancialDiagnosis, Long> {

    List<FinancialDiagnosis> findByUserId(Long userId);

    List<FinancialDiagnosis> findByUserIdAndIsActiveTrue(Long userId);
}
