package domain.model;

import lombok.Getter;
import lombok.Builder;
import lombok.With;

import java.time.LocalDateTime;

@Getter
@Builder
@With

public class User {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
    private LocalDateTime createdDate;

    public User () {
        if (email == null || email.isBlank()) throw new IllegalArgumentException("email is required");

        if (passwordHash == null || passwordHash.isBlank()) throw new IllegalArgumentException("password is required");
    }
}

