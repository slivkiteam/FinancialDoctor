package repository;

import model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByExternalUserId(String externalUserId);
    Optional<User> findByConfirmationToken(String token);
    Optional<User> findByEmail(String email);
}
