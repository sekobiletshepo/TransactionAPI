package infrastructure.repository;

import infrastructure.entity.TransactionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionJpaRepository extends JpaRepository<TransactionJpaEntity, Long> {

    Optional<TransactionJpaEntity> findByAccountIdAndExternalRef(
            Long accountId, String externalRef);

    @Query("""
            SELECT t
            FROM TransactionJpaEntity t
            WHERE t.accountId IN (
                SELECT a.id FROM AccountJpaEntity a WHERE a.userId = :userId
            )
              AND (:accountId IS NULL OR t.accountId = :accountId)
              AND (:categoryId IS NULL OR t.categoryId = :categoryId)
              AND (:from IS NULL OR t.transactionDate >= :from)
              AND (:to IS NULL OR t.transactionDate < :to)
            ORDER BY t.transactionDate DESC
            """)
    List<TransactionJpaEntity> search(
            @Param("userId") Long userId,
            @Param("accountId") Long accountId,
            @Param("categoryId") Long categoryId,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to);

    @Query(value = """
            SELECT t.category_id,
                   COALESCE(c.name, 'UNCATEGORIZED'),
                   COALESCE(SUM(CASE WHEN t.direction = 'DEBIT' THEN t.amount ELSE 0 END), 0),
                   COALESCE(SUM(CASE WHEN t.direction = 'CREDIT' THEN t.amount ELSE 0 END), 0),
                   COALESCE(SUM(t.amount), 0),
                   COUNT(*)
            FROM transactions t
            JOIN accounts a ON a.id = t.account_id
            LEFT JOIN categories c
                   ON c.id = t.category_id
                  AND (c.user_id IS NULL OR c.user_id = :userId)
            WHERE a.user_id = :userId
              AND (:accountId IS NULL OR t.account_id = :accountId)
              AND (:categoryId IS NULL OR t.category_id = :categoryId)
              AND (:from IS NULL OR t.transaction_date >= :from)
              AND (:to IS NULL OR t.transaction_date < :to)
            GROUP BY t.category_id, c.name
            ORDER BY c.name
            """, nativeQuery = true)
    List<Object[]> aggregateByCategory(
            @Param("userId") Long userId,
            @Param("accountId") Long accountId,
            @Param("categoryId") Long categoryId,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to);

}
