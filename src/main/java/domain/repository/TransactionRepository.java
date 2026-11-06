package domain.repository;

import domain.model.Transaction;

import java.lang.ScopedValue;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

/**
  This interface is the PORT - the application ring owns it and the infrastructure ring implements it
 **/

public interface TransactionRepository {

   Transaction save(Transaction transaction);

   Optional<Transaction> findByAccountIdAndExternalTransactionRef(Long accountId, String externalTransactionRef);

   List<Transaction> search(TransactionQuery query);

   List<CategoryAggregate> aggregateByCategory(TransactionQuery query);

   Optional<Transaction> findById(Long transactionId);

   record TransactionQuery(
           Long userId,
           Long accountId,
           Long categoryId,
           LocalDateTime from,
           LocalDateTime to) {}


    record CategoryAggregate(
            Long categoryId,
            String categoryName,
            BigDecimal totalDebits, //SUM of debit amounts
            BigDecimal totalCredits, //SUM of credit amounts
            BigDecimal total,
            Long count) {}
}
