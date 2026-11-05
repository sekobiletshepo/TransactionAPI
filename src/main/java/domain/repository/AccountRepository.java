package domain.repository;

import domain.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {

    Account save(Account account);

    Optional<Account> findById(Long id);

    /* All accounts owned by a user */
    List<Account> findByUserId(Long userId);

    Optional<Account> findBySourceAndExternalAccountRef(Long sourceId, String externalAccountRef);

    /* Prevents duplicate accounts */
    Optional<Account> findByUserIdAndSourceIdAndExternalAccountRef(Long userId,Long sourceId, String externalAccountRef);

    boolean existsByUserIdAndSourceIdAndExternalAccountRef(Long userId,Long sourceId, String externalAccountRef);

    void deleteById(Long accountId);
}
