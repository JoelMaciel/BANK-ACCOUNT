package com.bank.contas.domain.repositories;

import com.bank.contas.domain.models.AccountClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountClientRepository extends JpaRepository<AccountClient, UUID> {
}
