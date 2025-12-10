package ru.slivki.financial_doctor.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "accounts",
        uniqueConstraints = @UniqueConstraint(columnNames = "external_account_id"))
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "external_account_id", nullable = false)
    private String externalAccountId;

    @Column(name = "account_type", nullable = false, length = 20)
    private String accountType;

    @Column(name = "account_name")
    private String accountName;

    @Column(nullable = false, length = 3)
    private String currency = "RUB";

    private BigDecimal balance;
    private BigDecimal creditLimit;

    @Column(name = "masked_number", length = 20)
    private String maskedNumber;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
}
