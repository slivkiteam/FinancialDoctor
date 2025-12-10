package ru.slivki.financial_doctor.web.controller;

import jakarta.validation.Valid;
import ru.slivki.financial_doctor.model.FinancialDiagnosis;
import ru.slivki.financial_doctor.model.TreatmentPlan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.slivki.financial_doctor.service.FinancialDiagnosisService;
import ru.slivki.financial_doctor.web.dto.diagnosis.FinancialDiagnosisResponse;
import ru.slivki.financial_doctor.web.dto.diagnosis.TreatmentPlanResponse;
import ru.slivki.financial_doctor.web.dto.diagnosis.UpdateTreatmentPlanRequest;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class FinancialDiagnosisController {

    private final FinancialDiagnosisService financialDiagnosisService;

    public FinancialDiagnosisController(final FinancialDiagnosisService financialDiagnosisService) {
        this.financialDiagnosisService = financialDiagnosisService;
    }

    @PostMapping("/diagnoses/recalculate")
    public ResponseEntity<FinancialDiagnosisResponse> recalculateDiagnosis(@RequestParam("userId") final Long userId) {
        FinancialDiagnosis diagnosis = financialDiagnosisService.recalculateDiagnosis(userId);
        List<TreatmentPlan> plans = financialDiagnosisService.getCurrentTreatmentPlans(userId);
        return ResponseEntity.ok(toDiagnosisResponse(diagnosis, plans));
    }

    @GetMapping("/diagnoses/current")
    public ResponseEntity<FinancialDiagnosisResponse> getCurrentDiagnosis(@RequestParam("userId") final Long userId) {
        FinancialDiagnosis diagnosis = financialDiagnosisService.getCurrentDiagnosis(userId);
        if (diagnosis == null) {
            return ResponseEntity.notFound().build();
        }
        List<TreatmentPlan> plans = financialDiagnosisService.getCurrentTreatmentPlans(userId);
        return ResponseEntity.ok(toDiagnosisResponse(diagnosis, plans));
    }

    @GetMapping("/diagnoses/history")
    public ResponseEntity<List<FinancialDiagnosisResponse>> getDiagnosisHistory(@RequestParam("userId") final Long userId) {
        List<FinancialDiagnosis> diagnoses = financialDiagnosisService.getDiagnosisHistory(userId);
        return ResponseEntity.ok(
                diagnoses.stream()
                        .map(d -> toDiagnosisResponse(d, List.of()))
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/treatment-plans/current")
    public ResponseEntity<List<TreatmentPlanResponse>> getCurrentTreatmentPlans(@RequestParam("userId") final Long userId) {
        List<TreatmentPlan> plans = financialDiagnosisService.getCurrentTreatmentPlans(userId);
        return ResponseEntity.ok(
                plans.stream()
                        .map(this::toTreatmentPlanResponse)
                        .collect(Collectors.toList())
        );
    }

    @PatchMapping("/treatment-plans/{id}")
    public ResponseEntity<TreatmentPlanResponse> updateTreatmentPlan(@PathVariable("id") final Long id,
                                                                     @Valid @RequestBody final UpdateTreatmentPlanRequest request) {
        TreatmentPlan updated = financialDiagnosisService.updateTreatmentPlanProgress(
                id,
                request.getProgressPercentage(),
                request.getStatus(),
                request.getCurrentValue()
        );
        return ResponseEntity.ok(toTreatmentPlanResponse(updated));
    }

    private FinancialDiagnosisResponse toDiagnosisResponse(final FinancialDiagnosis diagnosis,
                                                           final List<TreatmentPlan> plans) {
        return FinancialDiagnosisResponse.builder()
                .id(diagnosis.getId())
                .userId(diagnosis.getUser().getId())
                .diagnosisType(diagnosis.getDiagnosisType())
                .diagnosisDate(diagnosis.getDiagnosisDate())
                .analysisPeriodStart(diagnosis.getAnalysisPeriodStart())
                .analysisPeriodEnd(diagnosis.getAnalysisPeriodEnd())
                .financialHealthScore(diagnosis.getFinancialHealthScore())
                .spendingScore(diagnosis.getSpendingScore())
                .savingScore(diagnosis.getSavingScore())
                .borrowingScore(diagnosis.getBorrowingScore())
                .planningScore(diagnosis.getPlanningScore())
                .isActive(diagnosis.getIsActive())
                .createdAt(diagnosis.getCreatedAt())
                .treatmentPlans(
                        plans.stream()
                                .map(this::toTreatmentPlanResponse)
                                .collect(Collectors.toList())
                )
                .build();
    }

    private TreatmentPlanResponse toTreatmentPlanResponse(final TreatmentPlan plan) {
        return TreatmentPlanResponse.builder()
                .id(plan.getId())
                .diagnosisId(plan.getDiagnosis().getId())
                .userId(plan.getUser().getId())
                .planType(plan.getPlanType())
                .title(plan.getTitle())
                .description(plan.getDescription())
                .priority(plan.getPriority())
                .status(plan.getStatus())
                .targetMetric(plan.getTargetMetric())
                .targetValue(plan.getTargetValue())
                .currentValue(plan.getCurrentValue())
                .progressPercentage(plan.getProgressPercentage())
                .startDate(plan.getStartDate())
                .targetDate(plan.getTargetDate())
                .completedAt(plan.getCompletedAt())
                .createdAt(plan.getCreatedAt())
                .updatedAt(plan.getUpdatedAt())
                .build();
    }
}


