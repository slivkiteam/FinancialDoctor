package ru.slivki.financial_doctor.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Separate datasource for the mock bank (H2).
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "ru.slivki.financial_doctor.bank.repository",
        entityManagerFactoryRef = "bankEntityManagerFactory",
        transactionManagerRef = "bankTransactionManager"
)
public class BankDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.bank.datasource")
    public DataSourceProperties bankDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource bankDataSource(@Qualifier("bankDataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean bankEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("bankDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("ru.slivki.financial_doctor.bank.model")
                .persistenceUnit("bank")
                .build();
    }

    @Bean
    public PlatformTransactionManager bankTransactionManager(
            @Qualifier("bankEntityManagerFactory") LocalContainerEntityManagerFactoryBean bankEntityManagerFactory) {
        return new JpaTransactionManager(bankEntityManagerFactory.getObject());
    }
}


