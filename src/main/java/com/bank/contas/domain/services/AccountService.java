package com.bank.contas.domain.services;

import com.bank.contas.api.models.AccountDto;
import com.bank.contas.domain.models.Account;
import com.bank.contas.domain.models.Agency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AccountService {
    Account save(Account account);

    void delete(UUID accountId);

    Page<AccountDto> findAll(Pageable pageable);

     Account searchOrFail(UUID accountId);
}
