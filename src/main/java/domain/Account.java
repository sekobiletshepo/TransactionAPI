package domain;

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

        if (userId == null) throw new IllegalArgumentException("userId is null");

        if (sourceId == null) throw new  IllegalArgumentException("sourceId is null");

        if (accountId == null) throw new  IllegalArgumentException("accountId is null");

        if (currency == null) throw new  IllegalArgumentException("currency is null");
    }
}
