package infrastructure.adapter;

import domain.model.Transaction;
import domain.repository.TransactionRepository;
import domain.repository.TransactionRepository.CategoryAggregate;
import domain.repository.TransactionRepository.TransactionQuery;
import infrastructure.mapper.TransactionMapper;
import infrastructure.repository.TransactionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TransactionRepositoryAdapter implements TransactionRepository {

    private final TransactionJpaRepository jpaRepository;
    private final TransactionMapper mapper;

    @Override
    public Transaction save(Transaction transaction) {
        return mapper.toDomain(jpaRepository.save(mapper.toJpa(transaction)));
    }

    @Override
    public Optional<Transaction> findByAccountIdAndExternalTransactionRef(
            Long accountId, String externalTransactionRef) {
        return jpaRepository.findByAccountIdAndExternalRef(accountId, externalTransactionRef)
                .map(mapper::toDomain);
    }

    @Override
    public List<Transaction> search(TransactionQuery query) {
        return jpaRepository.search(
                        query.userId(),
                        query.accountId(),
                        query.categoryId(),
                        query.from(),
                        query.to())
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<CategoryAggregate> aggregateByCategory(TransactionQuery query) {
        return jpaRepository.aggregateByCategory(
                        query.userId(),
                        query.accountId(),
                        query.categoryId(),
                        query.from(),
                        query.to())
                .stream()
                .map(this::toCategoryAggregate)
                .toList();
    }

    @Override
    public Optional<Transaction> findById(Long transactionId) {
        return jpaRepository.findById(transactionId).map(mapper::toDomain);
    }

    private CategoryAggregate toCategoryAggregate(Object[] row) {
        return new CategoryAggregate(
                row[0] == null ? null : ((Number) row[0]).longValue(),
                (String) row[1],
                decimal(row[2]),
                decimal(row[3]),
                decimal(row[4]),
                ((Number) row[5]).longValue());
    }

    private BigDecimal decimal(Object value) {
        return value == null ? BigDecimal.ZERO : new BigDecimal(value.toString());
    }
}
