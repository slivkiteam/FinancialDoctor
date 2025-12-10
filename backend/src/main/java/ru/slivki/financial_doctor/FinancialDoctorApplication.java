package ru.slivki.financial_doctor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"model", "repository", "service", "web", "ru.slivki.financial_doctor"})
public class FinancialDoctorApplication {

    public static void main(final String[] args) {
        SpringApplication.run(FinancialDoctorApplication.class, args);
    }
}


