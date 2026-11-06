package domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.With;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Builder
@With

public class Transaction {

    private Long transactionId;
    private Long accountId;
    private Long categoryId;
    private String externalRef;
    private BigDecimal amount;
    private String currency;
    private Direction direction; // Debit or Credit
    private LocalDateTime transactionDate;
    private String merchantName;
    private String rawDescription;
    private CategorizationMethod categorizationMethod;
    private Instant createdAt;


    public enum Direction {DEBIT, CREDIT}

    public enum CategorizationMethod {AUTO, MANUAL}

    public boolean isDebit() {
        return direction == Direction.DEBIT;
    }

    public boolean isCredit() {
        return direction == Direction.CREDIT;
    }

    public Transaction categorize(Long categoryId, CategorizationMethod method) {
        return this.withCategoryId(categoryId).withCategorizationMethod(method);
    }
}
