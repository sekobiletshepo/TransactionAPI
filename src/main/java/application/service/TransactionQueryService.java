package application.service;

import application.service.dto.TransactionDto;
import domain.model.Transaction;
import domain.repository.AccountRepository;
import domain.repository.TransactionRepository;
import domain.repository.TransactionRepository.TransactionQuery;
import domain.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class TransactionQueryService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public List<TransactionDto> findTransactions(Long userId, TransactionQuery query) {
        if (query.accountId() != null) {
            assertAccountOwnedByUser(query.accountId(), userId);
        }

        TransactionQuery scoped = new TransactionQuery(
                userId,
                query.accountId(),
                query.categoryId(),
                query.from(),
                query.to());

        return transactionRepository.search(scoped)
                .stream()
                .map(this::toDto)
                .toList();
    }


    public TransactionDto getTransaction(Long userId, Long transactionId) {
        Transaction tx = (Transaction) transactionRepository.findById(transactionId)
                .orElseThrow(() -> new DomainException(
                        "TX_NOT_FOUND ", "transaction " + transactionId + " not found"));
        assertAccountOwnedByUser(tx.getAccountId(), userId);
        return toDto(tx);
    }

    private void assertAccountOwnedByUser(Long accountId, Long userId) {
        Boolean owned = accountRepository.findById(accountId)
                .map(a -> userId.equals(a.getUserId()))
                .orElseThrow(() -> new DomainException(
                        "ACCOUNT_NOT_FOUND ", "account " + accountId + " not found"));

        if (!owned) {
            throw new DomainException("ACCOUNT_NOT_FOUND ", "account " + accountId + " not found");
        }
    }

    private TransactionDto toDto(Transaction t) {
        return TransactionDto.builder()
                .transactionId(t.getTransactionId())
                .accountId(t.getAccountId())
                .externalTransactionRef(t.getExternalTransactionRef())
                .amount(t.getAmount())
                .currency(t.getCurrency())
                .transactionType(t.getType())
                .transactionDate(t.getTransactionDate())
                .merchantName(t.getMerchantName())
                .rawDescription(t.getRawDescription())
                .categoryId(t.getCategoryId())
                .categorizationMethod(t.getCategorizationMethod())
                .createdDate(t.getCreatedDate())
                .build();
    }
}
