package ru.slivki.financial_doctor.bank.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateConsentRequest {

    private Data data;

    @Getter
    @Setter
    public static class Data {
        private List<String> permissions;
        private String expirationDateTime;
        private String transactionFromDateTime;
        private String transactionToDateTime;
    }
}



