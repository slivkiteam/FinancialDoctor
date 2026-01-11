package ru.slivki.financial_doctor.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import ru.slivki.financial_doctor.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import ru.slivki.financial_doctor.model.User;
import org.springframework.stereotype.Service;
import ru.slivki.financial_doctor.repository.UserRepository;

@AllArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User create(User user) {
        var passwordEncode = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncode);
        return userRepository.save(user);
    }

    @Transactional
    public User update(Long id, User user) {
        var userCur = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User with id %s not found".formatted(id))
        );
        userCur.setName(user.getName());
        var passwordEncode = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncode);
        return userRepository.save(userCur);
    }


    public void confirmEmail(String confirmationToken) {
        User user = userRepository.findByConfirmationToken(confirmationToken)
                .orElseThrow(() -> new ResourceNotFoundException("User not found by confirmation token"));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public User getById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
