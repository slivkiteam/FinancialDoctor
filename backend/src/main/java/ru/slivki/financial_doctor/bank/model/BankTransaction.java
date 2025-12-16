package ru.slivki.financial_doctor.bank.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "bank_transactions", indexes = {
        @Index(name = "idx_bank_txn_account", columnList = "account_id"),
        @Index(name = "idx_bank_txn_user", columnList = "user_id")
})
public class BankTransaction {

    @Id
    @Column(name = "transaction_id", nullable = false, length = 64)
    private String transactionId;

    @Column(name = "account_id", nullable = false, length = 64)
    private String accountId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "transaction_date_time", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime transactionDateTime;

    @Column(name = "amount", precision = 19, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency = "RUB";

    @Column(name = "credit_debit_indicator", nullable = false, length = 10)
    private String creditDebitIndicator;

    @Column(name = "merchant_name")
    private String merchantName;

    @Column(name = "transaction_information")
    private String transactionInformation;
}



