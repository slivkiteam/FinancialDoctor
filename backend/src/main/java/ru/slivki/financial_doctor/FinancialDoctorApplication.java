package ru.slivki.financial_doctor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"model", "repository", "service", "web", "config", "ru.slivki.financial_doctor"})
@EnableJpaRepositories(basePackages = {"repository"})
@EntityScan(basePackages = {"model"})
public class FinancialDoctorApplication {

    public static void main(final String[] args) {
        SpringApplication.run(FinancialDoctorApplication.class, args);
    }
}


