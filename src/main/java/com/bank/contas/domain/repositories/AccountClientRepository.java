package com.bank.contas.domain.repositories;

import com.bank.contas.domain.models.Account;
import com.bank.contas.domain.models.AccountClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountClientRepository extends JpaRepository<AccountClient, UUID> {

    boolean existsByAccountAndClientId(Account account, UUID clientId);

    Optional<AccountClient> findByAccountAccountId(UUID accountId);

    boolean existsByClientId(UUID clientId);

    void deleteAllByClientId(UUID clientId);
}
