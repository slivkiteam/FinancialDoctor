package ru.slivki.financial_doctor.web.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.slivki.financial_doctor.model.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.slivki.financial_doctor.service.AuthService;
import ru.slivki.financial_doctor.service.UserService;
import ru.slivki.financial_doctor.web.dto.auth.JwtRequest;
import ru.slivki.financial_doctor.web.dto.auth.JwtResponse;
import ru.slivki.financial_doctor.web.dto.user.UserCreatedDto;
import ru.slivki.financial_doctor.web.mapper.UserMapperImpl;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;
    UserService userService;
    UserMapperImpl userMapper;

    @PostMapping("/login")
    public JwtResponse login(@Validated @RequestBody JwtRequest jwtRequest) {
        return authService.login(jwtRequest);
    }

    @PostMapping("/register")
    public UserCreatedDto register(@RequestBody UserCreatedDto userCreatedDto) {
        User user = userMapper.toEntity(userCreatedDto);
        User createdUser = userService.create(user);
        return userMapper.toDto(createdUser);
    }

    @GetMapping("/confirm-account")
    public void confirmUserAccount(@RequestParam("token") String confirmationToken) {
        userService.confirmEmail(confirmationToken);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody String refreshToken) {
        return authService.refresh(refreshToken);
    }

}
