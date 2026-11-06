package infrastructure.repository;

import infrastructure.entity.TransactionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionJpaRepository extends JpaRepository<TransactionJpaEntity, Long> {

    Optional<TransactionJpaEntity> findByAccountIdAndExternalAccountRef(
            Long accountId, String externalAccountRef);

    @Query("""
        SELECT t.categoryId, COALESCE(c.name, 'UNCATEGORIZED'),
                SUM(t.amount), COUNT(t)
             FROM TransactionJpaEntity t
             LEFT JOIN CategoryJpaEntity c ON c.id = t.categoryId
             WHERE (:accountId IS NULL OR t.accountId = :accountId)
             AND (:categoryId IS NULL OR t.categoryId =: categoryId)
             AND t.transactionDate BETWEEN :from AND :to
             GROUP BY t.categoryId, c.name
             """)

    List<Object[]> aggregateByCategory(Long accountId, Long categoryId, LocalDateTime from, LocalDateTime to);

}
