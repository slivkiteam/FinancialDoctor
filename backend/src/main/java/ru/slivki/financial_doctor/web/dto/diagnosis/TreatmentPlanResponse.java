package ru.slivki.financial_doctor.web.dto.diagnosis;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TreatmentPlanResponse {

    Long id;
    Long diagnosisId;
    Long userId;
    String planType;
    String title;
    String description;
    String priority;
    String status;
    String targetMetric;
    BigDecimal targetValue;
    BigDecimal currentValue;
    Integer progressPercentage;
    LocalDate startDate;
    LocalDate targetDate;
    LocalDateTime completedAt;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}


