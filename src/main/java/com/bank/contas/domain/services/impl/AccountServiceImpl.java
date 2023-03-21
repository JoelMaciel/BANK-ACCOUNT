package com.bank.contas.domain.services.impl;

import com.bank.contas.api.clients.ClientRequestClient;
import com.bank.contas.api.models.converter.accounts.AccountDTOToDomain;
import com.bank.contas.api.models.converter.accounts.AccountToDTO;
import com.bank.contas.api.models.request.AccountDTO;
import com.bank.contas.api.models.request.AccountDTOUpdate;
import com.bank.contas.api.models.response.AccountSummaryDTO;
import com.bank.contas.domain.exceptions.AccountNotExistsException;
import com.bank.contas.domain.exceptions.EntityNotExistsException;
import com.bank.contas.domain.exceptions.NumberAccountInUseException;
import com.bank.contas.domain.models.Account;
import com.bank.contas.domain.models.AccountClient;
import com.bank.contas.domain.repositories.AccountClientRepository;
import com.bank.contas.domain.repositories.AccountRepository;
import com.bank.contas.domain.repositories.AgencyRepository;
import com.bank.contas.domain.services.AccountClientService;
import com.bank.contas.domain.services.AccountService;
import com.bank.contas.infrastructure.specification.SpecificationTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private static final String MSG_ACCOUNT_IN_USE =
            "Code account %s cannot be removed as it is in use";

    private final AccountRepository accountRepository;
    private final AccountClientRepository accountClientRepository;
    private final AgencyRepository agencyRepository;
    private final AccountDTOToDomain accountDTOToDomain;
    private final AccountToDTO accountToDto;

    private final ClientRequestClient clientRequestClient;

    @Override
    public Page<AccountSummaryDTO> findAll(Specification<Account> spec, UUID clientId, Pageable pageable) {
        Page<Account> accountPage = null;
        if (clientId != null) {
            accountPage = accountRepository.findAll(SpecificationTemplate.accountClientId(clientId).and(spec), pageable);
        } else {
            accountPage = accountRepository.findAll(pageable);
        }
        return accountToDto.converterToPageDto(accountPage, pageable);
    }

    @Override
    public AccountSummaryDTO updateAccount(UUID accountId, AccountDTOUpdate accountUpdate) {
        Account account = searchOrFail(accountId);
        accountDTOToDomain.copyToDomainObjectSummary(accountUpdate, account);
        accountRepository.save(account);
        return accountToDto.converterSumarry(account);
    }

    @Override
    public AccountSummaryDTO findByAccount(UUID accountId) {
        return accountToDto.converterSumarry(searchOrFail(accountId));
    }

    @Transactional
    @Override
    public AccountDTO save(AccountDTO accountDTO) {
        try {
            String agencyNumber = accountDTO.getNumberAgency();
            var agency = agencyRepository.findByNumber(agencyNumber);

            existsAccountNumber(accountDTO.getNumber());

            var account = accountDTOToDomain.toDomainObject(accountDTO);
            account.setAgency(agency.get());
            accountRepository.save(account);
            return accountToDto.converter(account);
        } catch (DataIntegrityViolationException e) {
            throw new NumberAccountInUseException("An account with that number already exists.");
        }
    }

    @Transactional
    @Override
    public void delete(UUID accountId) {
        boolean deleteAccountClientInClient = false;
        var accountClient = accountClientRepository.findByAccountAccountId(accountId);
        if (accountClient.isPresent()) {
            accountClientRepository.delete(accountClient.get());
            deleteAccountClientInClient = true;
        }
        accountRepository.delete(searchOrFail(accountId));
        if (deleteAccountClientInClient) {
            clientRequestClient.deleteAccountInClient(accountId);
        }
    }

    @Override
    public boolean existsAccountNumber(String number) {
        boolean accountNumber = accountRepository.existsByNumber(number);
        if (accountNumber) {
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


