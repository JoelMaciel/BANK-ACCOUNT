package com.bank.contas.domain.services;

import com.bank.contas.api.models.request.AccountDTO;
import com.bank.contas.api.models.request.AccountDTOUpdate;
import com.bank.contas.api.models.response.AccountSummaryDTO;
import com.bank.contas.domain.models.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface AccountService {
    AccountDTO save(AccountDTO accountDTO);

    void delete(UUID accountId);

    Page<AccountSummaryDTO> findAll(Specification<Account> spec, UUID clientId, Pageable pageable);

    boolean existsAccountNumber(String number);


    AccountSummaryDTO updateAccount(UUID accountId, AccountDTOUpdate accountUpdate);

    AccountSummaryDTO findByAccount(UUID accountId);

    Account searchOrFail(UUID accountId);


}
