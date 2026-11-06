package domain.model;

import domain.exception.DomainException;
import lombok.Builder;
import lombok.Getter;
import lombok.With;

import java.math.BigDecimal;

@Getter
@Builder
@With

public class Account {

    private Long accountId;
    private Long userId;
    private String sourceId;
    private AccountType accountType;
    private String externalAccountRef;
    private String displayName;
    private BigDecimal currentBalance;
    private String currency;

    public enum AccountType { SAVINGS, CREDIT }

    public Account () {

        if (userId == null) throw new DomainException("USER_ID_NOT_FOUND", null + " User not found");

        if (sourceId == null) throw new DomainException("SOURCE_ID_NOT_FOUND", null + " sourceId not found");

        if (accountId == null) throw new DomainException("ACCOUNT_ID_NOT_FOUND", null + " Account not found");

        if (currency == null) throw new DomainException("CURRENCY_NOT_FOUND", null + " Currency not found");
    }
}
