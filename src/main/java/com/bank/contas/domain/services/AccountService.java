package com.bank.contas.domain.services;

import com.bank.contas.api.models.AccountDto;
import com.bank.contas.domain.models.Account;
import com.bank.contas.infrastructure.specification.SpecificationTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.UUID;

public interface AccountService {
    Account save(Account account);

    void delete(UUID accountId);

    Page<AccountDto> findAll(Specification<Account> spec, Pageable pageable);

     Account searchOrFail(UUID accountId);

     boolean existsAccountNumber(String number);
}
