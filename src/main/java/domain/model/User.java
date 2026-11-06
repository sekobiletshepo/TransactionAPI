package domain.model;

import domain.exception.DomainException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Builder;
import lombok.With;

import java.time.LocalDateTime;

@Getter
@Builder
@With
@AllArgsConstructor

public class User {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
    private LocalDateTime createdDate;

    public User () {
        if (email == null || email.isBlank()) throw new DomainException("EMAIL_NOT_FOUND", userId + "email is required");

        if (passwordHash == null || passwordHash.isBlank()) throw new DomainException("PASSWORD_HASH_NOT_FOUND", userId + "passwordHash is required");
    }
}
