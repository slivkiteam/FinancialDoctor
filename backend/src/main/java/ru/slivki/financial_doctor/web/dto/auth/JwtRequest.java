package ru.slivki.financial_doctor.web.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtRequest {

    @NotNull(message = "Username can`t be NULL")
    String username;

    @NotNull(message = "Password can`t be NULL")
    String password;
}
