package com.bank.contas.domain.repositories;

import com.bank.contas.domain.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    boolean existsByNumber(String number);

}
