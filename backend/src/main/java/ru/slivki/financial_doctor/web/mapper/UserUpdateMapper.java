package ru.slivki.financial_doctor.web.mapper;

import org.springframework.stereotype.Component;
import ru.slivki.financial_doctor.model.User;
import ru.slivki.financial_doctor.web.dto.user.UserUpdateDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserUpdateMapper implements Mappable<User, UserUpdateDto> {

    @Override
    public User toEntity(UserUpdateDto dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setName(dto.getName());
        user.setPassword(dto.getPassword());
        return user;
    }

    @Override
    public UserUpdateDto toDto(User entity) {
        if (entity == null) {
            return null;
        }
        UserUpdateDto dto = new UserUpdateDto();
        dto.setName(entity.getName());
        dto.setPassword(entity.getPassword());
        return dto;
    }

    @Override
    public List<UserUpdateDto> toDto(List<User> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> toEntity(List<UserUpdateDto> dtos) {
        if (dtos == null) {
            return null;
        }
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}