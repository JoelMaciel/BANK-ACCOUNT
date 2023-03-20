package com.bank.contas.domain.repositories;

import com.bank.contas.domain.models.Account;
import com.bank.contas.domain.models.AccountClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountClientRepository extends JpaRepository<AccountClient, UUID> {

    boolean existsByAccountAndClientId(Account account, UUID clientId);

    @Query(value = "select * from tb_accounts_clients where account_id = :accountId", nativeQuery = true)
    AccountClient findAllAccountClientIntoAccount(@Param("accountId") UUID accountId);
}
