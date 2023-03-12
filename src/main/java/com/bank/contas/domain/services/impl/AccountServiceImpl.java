package com.bank.contas.domain.services.impl;

import com.bank.contas.api.models.AccountDto;
import com.bank.contas.api.models.converter.AccountToDto;
import com.bank.contas.domain.exceptions.AccountNotExistsException;
import com.bank.contas.domain.exceptions.EntityInUseException;
import com.bank.contas.domain.exceptions.NumberAccountInUseException;
import com.bank.contas.domain.models.Account;
import com.bank.contas.domain.repositories.AccountRepository;
import com.bank.contas.domain.services.AccountService;
import com.bank.contas.domain.services.AgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    private static final String MSG_ACCOUNT_IN_USE =
            "Code account %s cannot be removed as it is in use";

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private AccountToDto accountToDto;

    @Override
    public Account save(Account account) {
        try {
            UUID agencyId = account.getAgency().getAgencyId();
            agencyService.searchOrFail(agencyId);

            existsAccountNumber(account.getNumber());

            return accountRepository.save(account);
        } catch (DataIntegrityViolationException e) {
            throw  new NumberAccountInUseException("An account with that number already exists.");
        }
    }

    @Transactional
    @Override
    public void delete(UUID accountId) {
        try {
            accountRepository.deleteById(accountId);
            accountRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw  new AccountNotExistsException(accountId);
        } catch (DataIntegrityViolationException e) {
            throw  new EntityInUseException(String.format(MSG_ACCOUNT_IN_USE, accountId));
        }

    }

    @Override
    public Page<AccountDto> findAll(Pageable pageable) {
        Page<Account> accountPage = accountRepository.findAll(pageable);
        return accountToDto.converterToPageDto(accountPage, pageable);
    }

    @Override
    public Account searchOrFail(UUID accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotExistsException(accountId));
    }

    @Override
    public boolean existsAccountNumber(String number) {
       return accountRepository.existsByNumber(number);
    }
}


