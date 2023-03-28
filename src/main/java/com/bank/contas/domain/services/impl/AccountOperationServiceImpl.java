package com.bank.contas.domain.services.impl;

import com.bank.contas.domain.repositories.AccountRepository;
import com.bank.contas.domain.services.AccountOperationService;
import com.bank.contas.domain.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AccountOperationServiceImpl implements AccountOperationService {

    private final AccountRepository accountRepository;

    private final AccountService accountService;

    @Override
    public BigDecimal consultBalance(UUID accountId) {
        var account = accountService.findAccountId(accountId);
        return account.getBalance();
    }

    @Override
    public void deposit(UUID accountId, BigDecimal amount) {
        var account = accountService.findAccountId(accountId);
        account.deposit(amount);
        accountRepository.save(account);
    }

    @Override
    public void withdraw(UUID accountId, BigDecimal amount) {
            var account = accountService.findAccountId(accountId);
            account.withdraw(amount);
            accountRepository.save(account);
    }

    @Override
    public void transfer(UUID accountIdOrigin, UUID accountIdDestiny, BigDecimal amount) {
        var accountOrigin = accountService.findAccountId(accountIdOrigin);
        var accountDestiny = accountService.findAccountId(accountIdDestiny);
        accountOrigin.transfer(accountDestiny, amount);
        accountRepository.saveAll(Arrays.asList(accountOrigin, accountDestiny));

    }

}
