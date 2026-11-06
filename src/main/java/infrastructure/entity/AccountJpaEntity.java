package infrastructure.entity;

import domain.model.Account;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "accounts",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_account_source_external",
                columnNames = {"source_id", "external_account_ref"}),
        indexes = {
                @Index(name = "ix_account_user", columnList = "user_id"),
                @Index(name = "ix_account_source", columnList = "source_id")
        })

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AccountJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "source_id", nullable = false)
    private Long sourceId;

    @Column(name = "external_account_ref", nullable = false, length = 255)
    private String externalAccountRef;

    @Column(name = "display_name", nullable = false, length = 255)
    private String displayName;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false, length = 20)
    private Account.AccountType accountType;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @Column(name = "current_balance", nullable = false, precision = 19, scale = 4)
    private BigDecimal currentBalance;

    public enum AccountType {SAVINGS, CREDIT}
}
