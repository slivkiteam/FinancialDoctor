package ru.slivki.financial_doctor.repository;

import ru.slivki.financial_doctor.model.TreatmentPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreatmentPlanRepository extends JpaRepository<TreatmentPlan, Long> {

    List<TreatmentPlan> findByUserId(Long userId);

    List<TreatmentPlan> findByUserIdAndStatus(Long userId, String status);

    List<TreatmentPlan> findByDiagnosisId(Long diagnosisId);
}
