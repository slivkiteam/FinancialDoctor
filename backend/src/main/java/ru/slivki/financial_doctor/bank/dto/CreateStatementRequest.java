package ru.slivki.financial_doctor.bank.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateStatementRequest {

    private Data data;

    @Getter
    @Setter
    public static class Data {
        private Statement statement;
    }

    @Getter
    @Setter
    public static class Statement {
        private String accountId;
        private String fromBookingDateTime;
        private String toBookingDateTime;
    }
}




