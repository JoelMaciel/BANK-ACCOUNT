package com.bank.contas.domain.services.impl;

import com.bank.contas.api.models.converter.AccountDTOToDomain;
import com.bank.contas.api.models.converter.AccountToDTO;
import com.bank.contas.api.models.request.AccountDTO;
import com.bank.contas.api.models.request.AccountDTOUpdate;
import com.bank.contas.domain.exceptions.AccountNotExistsException;
import com.bank.contas.domain.exceptions.EntityInUseException;
import com.bank.contas.domain.exceptions.EntityNotExistsException;
import com.bank.contas.domain.exceptions.NumberAccountInUseException;
import com.bank.contas.domain.models.Account;
import com.bank.contas.domain.models.Agency;
import com.bank.contas.domain.repositories.AccountRepository;
import com.bank.contas.domain.services.AccountService;
import com.bank.contas.domain.services.AgencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private static final String MSG_ACCOUNT_IN_USE =
            "Code account %s cannot be removed as it is in use";

    private final AccountRepository accountRepository;
    private final AgencyService agencyService;
    private final AccountDTOToDomain accountDTOToDomain;
    private final AccountToDTO accountToDto;

    @Override
    public Page<AccountDTO> findAll(Specification<Account> spec, Pageable pageable) {
        Page<Account> accountPage = accountRepository.findAll(spec ,pageable);
        return accountToDto.converterToPageDto(accountPage, pageable);
    }



    @Override
    public AccountDTO updateAccount(AccountDTOUpdate accountUpdate) {
        return null;
    }

    @Override
    public AccountDTO findByAccount(UUID accountId) {
       return accountToDto.converter(searchOrFail(accountId));
    }

    @Transactional
    @Override
    public AccountDTO save(AccountDTO accountDTO) {
        try {
            String agencyName= accountDTO.getNameAgency();
            var agency = agencyService.findByName(agencyName);

            existsAccountNumber(accountDTO.getNumber());

            var account = accountDTOToDomain.toDomainObject(accountDTO);
            account.getAgency().setName(agencyName);
            accountRepository.save(account);
            return accountToDto.converter(account);
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
    public boolean existsAccountNumber(String number) {
           boolean acountNumber  = accountRepository.existsByNumber(number);
           if(acountNumber) {
               throw new EntityNotExistsException("There is no account with that number");
           }
           return false;
    }

    @Override
    public Account searchOrFail(UUID accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotExistsException(accountId));
    }
}


