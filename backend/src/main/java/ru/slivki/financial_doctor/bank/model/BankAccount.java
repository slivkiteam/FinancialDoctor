package ru.slivki.financial_doctor.bank.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "bank_accounts")
public class BankAccount {

    @Id
    @Column(name = "account_id", nullable = false, length = 64)
    private String accountId;

    @Column(name = "account_type", nullable = false)
    private String accountType;

    @Column(name = "account_name")
    private String accountName;

    @Column(nullable = false, length = 3)
    private String currency = "RUB";

    @Column(precision = 19, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "credit_limit", precision = 19, scale = 2)
    private BigDecimal creditLimit = BigDecimal.ZERO;

    @Column(name = "masked_number", length = 20)
    private String maskedNumber;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}




