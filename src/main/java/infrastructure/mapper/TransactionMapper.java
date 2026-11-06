package infrastructure.mapper;

import domain.model.Transaction;
import infrastructure.entity.TransactionJpaEntity;
import org.springframework.stereotype.Component;

@Component

public class TransactionMapper {

    public Transaction toDomain(TransactionJpaEntity e) {
        return Transaction.builder()
                .transactionId(e.getId())
                .accountId(e.getAccountId())
                .categoryId(e.getCategoryId())
                .externalRef(e.getExternalRef())
                .amount(e.getAmount())
                .currency(e.getCurrency())
                .direction(Transaction.Direction.valueOf(e.getDirection().name()))
                .transactionDate(e.getTransactionDate())
                .merchantName(e.getMerchantName())
                .rawDescription(e.getRawDescription())
                .categorizationMethod(e.getCategorizationMethod() == null
                        ? null
                        : Transaction.CategorizationMethod.valueOf(e.getCategorizationMethod().name()))
                .build();
    }

    public TransactionJpaEntity toJpa(Transaction t) {
        return TransactionJpaEntity.builder()
                .id(t.getTransactionId())
                .accountId(t.getAccountId())
                .categoryId(t.getCategoryId())
                .externalRef(t.getExternalRef())
                .amount(t.getAmount())
                .currency(t.getCurrency())
                .direction(Transaction.Direction.valueOf(t.getDirection().name()))
                .transactionDate(t.getTransactionDate())
                .merchantName(t.getMerchantName())
                .rawDescription(t.getRawDescription())
                .categorizationMethod(t.getCategorizationMethod() == null
                        ? null
                        : Transaction.CategorizationMethod.valueOf(t.getCategorizationMethod().name()))
                .build();
    }
}
