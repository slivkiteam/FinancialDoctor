package model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_user_id", unique = true)
    private String externalUserId;

    private String email;
    private String name;

    @Column(name = "phone_number", length = 16)
    private String phoneNumber;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
}
