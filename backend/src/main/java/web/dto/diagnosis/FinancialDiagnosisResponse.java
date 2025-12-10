package web.dto.diagnosis;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FinancialDiagnosisResponse {

    Long id;
    Long userId;
    String diagnosisType;
    LocalDateTime diagnosisDate;
    LocalDate analysisPeriodStart;
    LocalDate analysisPeriodEnd;
    Integer financialHealthScore;
    Integer spendingScore;
    Integer savingScore;
    Integer borrowingScore;
    Integer planningScore;
    Boolean isActive;
    LocalDateTime createdAt;

    List<TreatmentPlanResponse> treatmentPlans;
}


