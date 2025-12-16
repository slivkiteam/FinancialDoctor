package ru.slivki.financial_doctor.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionListResponse {

    private Data data;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data {
        private List<Transaction> transaction;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Transaction {
        private String accountId;
        private String transactionId;
        private String transactionDateTime;
        private Amount amount;
        private String creditDebitIndicator;
        private String merchantName;
        private String transactionInformation;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Amount {
        private String amount;
        private String currency;
    }
}



