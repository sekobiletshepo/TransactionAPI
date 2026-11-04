package domain;

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
    private String externalTransactionRef;
    private BigDecimal amount;
    private String currency;
    private String type; // debit or credit
    private LocalDateTime transactionDate;
    private String merchantName;
    private String rawDescription;
    private CategorizationMethod categorizationMethod;
    private Instant createdDate;

    public enum Type { DEBIT, CREDIT }
    public enum CategorizationMethod { AUTO, MANUAL }

    public boolean isDebit() {
        return type.equals(Type.DEBIT);
    }

    public boolean isCredit() {
        return type.equals(Type.CREDIT);}
    }

    public Transaction categorize(Long categoryId, CategorizedMethod method) {
        return this.withCategoryId(categoryId).withCategoryMethod(method);
    }
