package ru.slivki.financial_doctor.service;

import ru.slivki.financial_doctor.model.FinancialDiagnosis;
import ru.slivki.financial_doctor.model.TreatmentPlan;

import java.math.BigDecimal;
import java.util.List;

public interface FinancialDiagnosisService {

    FinancialDiagnosis recalculateDiagnosis(Long userId);

    FinancialDiagnosis getCurrentDiagnosis(Long userId);

    List<FinancialDiagnosis> getDiagnosisHistory(Long userId);

    List<TreatmentPlan> getCurrentTreatmentPlans(Long userId);

    TreatmentPlan updateTreatmentPlanProgress(Long planId,
                                              Integer progressPercentage,
                                              String status,
                                              BigDecimal currentValue);
}