package service;

import exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import model.User;
import org.springframework.stereotype.Service;
import repository.UserRepository;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User create(User user) {
        return userRepository.save(user);
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
