package com.bank.contas.domain.services;

import com.bank.contas.api.models.request.AccountDTO;
import com.bank.contas.api.models.request.AccountDTOUpdate;
import com.bank.contas.api.models.response.AccountResponseDTO;
import com.bank.contas.domain.models.AccountModel;
import com.bank.contas.domain.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface AccountService {
    AccountResponseDTO save(AccountDTO accountDTO);

    void delete(UUID accountId);

    Page<AccountResponseDTO> findAll(Specification<AccountModel> spec, UUID clientId, Pageable pageable);

    boolean existsAccountNumber(String number);


    AccountResponseDTO updateAccount(UUID accountId, AccountDTOUpdate accountUpdate);

    AccountResponseDTO findByAccount(UUID accountId);

    AccountModel findAccountId(UUID accountId);

}
