package ru.slivki.financial_doctor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Explicitly enables JPA repositories for the primary datasource.
 * Needed because introducing a secondary datasource with @EnableJpaRepositories
 * for the bank mock disables Spring Boot's default repo scanning.
 */
@Configuration
@EnableJpaRepositories(basePackages = "ru.slivki.financial_doctor.repository")
public class PrimaryJpaConfig {
}


