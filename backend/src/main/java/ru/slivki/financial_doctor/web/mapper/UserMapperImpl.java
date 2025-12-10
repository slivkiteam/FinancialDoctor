package ru.slivki.financial_doctor.web.mapper;

import org.springframework.stereotype.Component;
import ru.slivki.financial_doctor.model.Role;
import ru.slivki.financial_doctor.model.User;
import ru.slivki.financial_doctor.web.dto.user.UserCreatedDto;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapperImpl implements Mappable<User, UserCreatedDto> {

    @Override
    public User toEntity(UserCreatedDto dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setPassword(dto.getPassword());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setIsActive(false);
        user.setEnabled(false);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER);
        user.setRoles(roles);
        user.setExternalUserId(null);
        user.setConfirmationToken(null);
        return user;
    }

    @Override
    public UserCreatedDto toDto(User entity) {
        if (entity == null) {
            return null;
        }
        UserCreatedDto dto = new UserCreatedDto();
        dto.setEmail(entity.getEmail());
        dto.setName(entity.getName());
        dto.setPhoneNumber(entity.getPhoneNumber());
        return dto;
    }

    @Override
    public List<UserCreatedDto> toDto(List<User> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> toEntity(List<UserCreatedDto> dtos) {
        if (dtos == null) {
            return null;
        }
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}