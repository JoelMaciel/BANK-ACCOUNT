package com.bank.contas.domain.services.impl;

import com.bank.contas.api.models.request.AccountDTO;
import com.bank.contas.api.models.request.AccountDTOUpdate;
import com.bank.contas.api.models.response.AccountResponseDTO;
import com.bank.contas.domain.exceptions.AccountNotFoundException;
import com.bank.contas.domain.exceptions.AgencyNotFoundException;
import com.bank.contas.domain.exceptions.EntityNotExistsException;
import com.bank.contas.domain.exceptions.NumberAccountInUseException;
import com.bank.contas.domain.models.AccountModel;
import com.bank.contas.domain.models.AgencyModel;
import com.bank.contas.domain.repositories.AccountRepository;
import com.bank.contas.domain.repositories.AgencyRepository;
import com.bank.contas.domain.services.AccountService;
import com.bank.contas.infrastructure.specification.SpecificationTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private static final String MSG_ACCOUNT_IN_USE =
            "Code account %s cannot be removed as it is in use";
    private final static String MSG_ACCOUNT_NOT_FOUND =
            "There is no agency registered with this number.";
    private final static String MSG_ACCOUNT_ID =
            "There is no agency registered with this Id.";

    private final AccountRepository accountRepository;
    private final AgencyRepository agencyRepository;


    @Transactional
    @Override
    public Page<AccountResponseDTO> findAll(Specification<AccountModel> spec, UUID userId, Pageable pageable) {
        if (userId != null) {
            Page<AccountModel> accountPage = accountRepository.findAll(
                    SpecificationTemplate.accountUserId(userId).and(spec), pageable);
            return getAccountResponseDTOS(pageable, accountPage);
        } else {
            Page<AccountModel> accountPage = accountRepository.findAll(pageable);
            return getAccountResponseDTOS(pageable, accountPage);
        }

    }

    private PageImpl<AccountResponseDTO> getAccountResponseDTOS(Pageable pageable, Page<AccountModel> accountPage) {
        List<AccountResponseDTO> accountList = accountPage.getContent().stream()
                .map(AccountResponseDTO::toDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(accountList, pageable, accountPage.getTotalElements());
    }

    @Transactional
    @Override
    public AccountResponseDTO updateAccount(UUID accountId, AccountDTOUpdate accountUpdate) {
        try {
            AccountModel accountModel = findAccountId(accountId).toBuilder()
                    .number(accountUpdate.getNumber())
                    .type(accountUpdate.getType())
                    .build();
            accountRepository.save(accountModel);
            accountRepository.flush();
            return AccountResponseDTO.toDTO(accountModel);
        } catch (EntityNotFoundException e) {
            throw new AccountNotFoundException(MSG_ACCOUNT_NOT_FOUND);
        }


    }

    @Override
    public AccountResponseDTO findByAccount(UUID accountId) {
        return AccountResponseDTO.toDTO(findAccountId(accountId));
    }

    @Transactional
    @Override
    public AccountResponseDTO save(AccountDTO accountDTO) {
        try {
            Optional<AgencyModel> agency = agencyRepository.findByNumber(accountDTO.getNumberAgency());
            existsAccountNumber(accountDTO.getNumber());
            AccountModel accountModel = accountRepository.save(AccountDTO.toEntity(accountDTO, agency.get()));
            accountRepository.flush();
            return AccountResponseDTO.toDTO(accountModel);
        } catch (DataIntegrityViolationException e) {
            throw new NumberAccountInUseException("An account with that number already exists.");
        } catch (NoSuchElementException e) {
            throw new AgencyNotFoundException(accountDTO.getNumberAgency());
        }
    }

    @Transactional
    @Override
    public void delete(UUID accountId) {
        accountRepository.delete(findAccountId(accountId));
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
    public AccountModel findAccountId(UUID accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(MSG_ACCOUNT_ID));
    }


}


