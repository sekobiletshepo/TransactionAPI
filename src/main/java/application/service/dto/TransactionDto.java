package application.service.dto;

import domain.model.Transaction;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

@Builder
@Value

public class TransactionDto {

    Long transactionId;
    Long categoryId;
    Long userId;
    Long accountId;
    BigDecimal amount;
    String currency;
    LocalDateTime transactionDate;
    String merchantName;
    String rawDescription;
    Transaction.Direction direction;
    Instant createdAt;
    Transaction.CategorizationMethod categorizationMethod;
    String externalRef;
}
