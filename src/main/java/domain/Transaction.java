package domain;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.Instant;

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

    public Transaction() {}

    public Transaction(Long accountId,
                       String externalTransactionRef,
                       BigDecimal amount,
                       String currency,
                       String type,
                       LocalDateTime transactionDate,
                       String merchantName,
                       String rawDescription) {
        this.accountId = accountId;
        this.externalTransactionRef = externalTransactionRef;
        this.amount = amount;
        this.currency = currency;
        this.type = type;
        this.transactionDate = transactionDate;
        this.merchantName = merchantName;
        this.rawDescription = rawDescription;
        this.categorizationMethod = CategorizationMethod.UNCATEGORIZED;
        this.createdDate = Instant.now();
    }

    //Getters

    public Long getId (Long id) { return id; }
    public Long getAccountId () { return accountId; }
    public Long getCategoryId () { return categoryId; }
    public String getExternalTransactionRef () { return externalTransactionRef; }
    public BigDecimal getAmount () { return amount; }
    public String getCurrency () { return currency; }
    public String getType () { return type; }
    public LocalDateTime getTransactionDate () { return transactionDate; }
    public String getMerchantName () { return merchantName; }
    public String getRawDescription () { return rawDescription; }
    public CategorizationMethod getCategorizationMethod()  { return categorizationMethod; }
    public Instant getCreatedDate () { return createdDate; }

}

    //Setters

    public void setId(Long id) {this.id = id; }

