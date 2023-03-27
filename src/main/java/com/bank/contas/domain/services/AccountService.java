package com.bank.contas.domain.services;

import com.bank.contas.api.dtos.request.AccountDTO;
import com.bank.contas.api.dtos.request.AccountDTOUpdate;
import com.bank.contas.api.dtos.response.AccountResponseDTO;
import com.bank.contas.domain.models.AccountModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface AccountService {

    AccountResponseDTO save(AccountDTO accountDTO, UUID userId);

    Page<AccountResponseDTO> findAll(Specification<AccountModel> spec, UUID clientId, Pageable pageable);

    boolean existsAccountNumber(String number);


    AccountResponseDTO updateAccount(UUID accountId, AccountDTOUpdate accountUpdate);

    AccountResponseDTO findByAccount(UUID accountId);

    AccountModel findAccountId(UUID accountId);

}
