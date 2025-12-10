package ru.slivki.financial_doctor.bank.model;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "bank_consents")
public class BankConsent {

    @Id
    @Column(name = "consent_id", nullable = false, length = 64)
    private String consentId;

    @ElementCollection
    @Column(name = "permission", nullable = false)
    private List<String> permissions = new ArrayList<>();

    @Column(name = "expiration", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime expirationDateTime;

    @Column(name = "txn_from", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime transactionFromDateTime;

    @Column(name = "txn_to", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime transactionToDateTime;

    @Column(name = "status", nullable = false)
    private String status = "Authorized";
}


