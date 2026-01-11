package ru.slivki.financial_doctor.web.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.slivki.financial_doctor.service.UserService;
import ru.slivki.financial_doctor.web.dto.user.UserUpdateDto;
import ru.slivki.financial_doctor.web.mapper.UserUpdateMapper;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/users")
public class UserController {

    UserService userService;
    UserUpdateMapper mapper;

    @PatchMapping("/{id}")
    public UserUpdateDto updateUser(@PathVariable Long id, @RequestBody UserUpdateDto userUpdateDto) {
        var user = mapper.toEntity(userUpdateDto);
        return mapper.toDto(userService.update(id, user));
    }
}

