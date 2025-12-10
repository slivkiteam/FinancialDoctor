package ru.slivki.financial_doctor.web.security;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {
    String secret;
    Long access;
    Long refresh;
}

