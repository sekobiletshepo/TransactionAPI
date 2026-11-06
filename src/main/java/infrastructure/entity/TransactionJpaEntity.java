package infrastructure.entity;

import domain.model.Transaction;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"account_id", "external_ref"}))

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TransactionJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "external_ref", nullable = false)
    private String externalRef;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(nullable = false,length = 3)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Transaction.Direction direction;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "merchant_name")
    private String merchantName;

    @Column(name = "raw_description")
    private String rawDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "categorization_method", length = 10)
    private Transaction.CategorizationMethod categorizationMethod;

    public enum Direction {DEBIT, CREDIT}

    public enum CategorizationMethod {AUTO, MANUAL}
}
