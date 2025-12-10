package ru.slivki.financial_doctor.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "financial_diagnoses")
public class FinancialDiagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "diagnosis_type", nullable = false)
    private String diagnosisType;

    @Column(name = "diagnosis_date", nullable = false)
    private LocalDateTime diagnosisDate = LocalDateTime.now();

    @Column(name = "analysis_period_start", nullable = false)
    private LocalDate analysisPeriodStart;

    @Column(name = "analysis_period_end", nullable = false)
    private LocalDate analysisPeriodEnd;

    @Column(name = "financial_health_score", nullable = false)
    private Integer financialHealthScore;

    @Column(name = "spending_score", nullable = false)
    private Integer spendingScore;

    @Column(name = "saving_score", nullable = false)
    private Integer savingScore;

    @Column(name = "borrowing_score", nullable = false)
    private Integer borrowingScore;

    @Column(name = "planning_score", nullable = false)
    private Integer planningScore;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
