package com.bank.contas.domain.repositories;

import com.bank.contas.domain.models.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountModel, UUID>, JpaSpecificationExecutor<AccountModel> {

    boolean existsByNumber(String number);


}
