package ru.slivki.financial_doctor.web.dto.auth;


import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Request for login")
public class JwtRequest {
    @Schema(description = "email", example = "mpetr4ova@gmail.com")
    @NotNull(message = "Username can`t be NULL")
    String username;
    @Schema(description = "password", example = "12345")
    @NotNull(message = "Password can`t be NULL")
    String password;
}
