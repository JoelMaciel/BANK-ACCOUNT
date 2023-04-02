package com.bank.contas.domain.services.impl;

import com.bank.contas.api.dtos.request.AccountDTO;
import com.bank.contas.api.dtos.request.AccountDTOUpdate;
import com.bank.contas.api.dtos.response.AccountResponseDTO;
import com.bank.contas.domain.exceptions.AccountNotExistException;
import com.bank.contas.domain.exceptions.AgencyNotFoundException;
import com.bank.contas.domain.exceptions.EntityNotExistsException;
import com.bank.contas.domain.exceptions.NumberAccountInUseException;
import com.bank.contas.domain.models.AccountModel;
import com.bank.contas.domain.repositories.AccountRepository;
import com.bank.contas.domain.repositories.AgencyRepository;
import com.bank.contas.domain.services.AccountService;
import com.bank.contas.domain.services.UserService;
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
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private static final String MSG_ACCOUNT_IN_USE =
            "An account with that number already exists.";
    private final static String MSG_ACCOUNT_NOT_FOUND =
            "There is no account registered with this number.";
    private final static String MSG_ACCOUNT_ID =
            "There is no account registered with this Id.";

    private final AccountRepository accountRepository;
    private final AgencyRepository agencyRepository;
    private final UserService userService;


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
            throw new AccountNotExistException(MSG_ACCOUNT_NOT_FOUND);
        }
    }

    @Override
    public AccountResponseDTO findByAccount(UUID accountId) {
        return AccountResponseDTO.toDTO(findAccountId(accountId));
    }

    @Transactional
    @Override
    public AccountResponseDTO save(AccountDTO accountDTO, UUID userId) {
        try {
            var agency = agencyRepository.findByNumber(accountDTO.getNumberAgency());
            existsAccountNumber(accountDTO.getNumber());
            var user = userService.findById(userId);
            AccountModel accountModel = accountRepository.save(AccountDTO.toEntity(accountDTO, agency.get(), user));
            accountRepository.flush();
            return AccountResponseDTO.toDTO(accountModel);
        } catch (DataIntegrityViolationException e) {
            throw new NumberAccountInUseException(MSG_ACCOUNT_IN_USE);
        } catch (NoSuchElementException e) {
            throw new AgencyNotFoundException(accountDTO.getNumberAgency());
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
    public AccountModel findAccountId(UUID accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotExistException(MSG_ACCOUNT_ID));
    }
}


