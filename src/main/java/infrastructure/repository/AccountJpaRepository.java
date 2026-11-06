package infrastructure.repository;

import infrastructure.entity.AccountJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountJpaRepository extends JpaRepository<AccountJpaEntity, Long> {

    List<AccountJpaEntity> findByUserId(Long userId);

    Optional<AccountJpaEntity> findBySourceIdAndExternalAccountRef(
            Long sourceId, String externalAccountRef);

    Optional<AccountJpaEntity> findByUserIdAndSourceIdAndExternalAccountRef(
            Long userId, Long sourceId, String externalAccountRef);

    boolean existsByUserIdAndSourceIdAndExternalAccountRef(
            Long userId, Long sourceId, String externalAccountRef);
}

