package ru.slivki.financial_doctor.bank.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "bank_statements")
public class BankStatement {

    @Id
    @Column(name = "statement_id", nullable = false, length = 64)
    private String statementId;

    @Column(name = "account_id", nullable = false, length = 64)
    private String accountId;

    @Column(name = "from_booking_date_time", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime fromBookingDateTime;

    @Column(name = "to_booking_date_time", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime toBookingDateTime;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdAt = OffsetDateTime.now();
}


