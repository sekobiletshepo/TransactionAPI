package domain.exception;

import lombok.Getter;

@Getter

/* Business-rule failure. thrown by the domain and application layers */

public class DomainException extends RuntimeException{

    private final String code;

    public DomainException(String code, String message) {
        super(message);
        this.code = code;
    }

    public DomainException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
