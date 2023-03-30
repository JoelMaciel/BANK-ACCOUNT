package com.bank.contas.domain.services.impl;

import com.bank.contas.api.dtos.event.StatementEventDTO;
import com.bank.contas.api.publishers.AccountEventPublisher;
import com.bank.contas.domain.enums.TransactionType;
import com.bank.contas.domain.repositories.AccountRepository;
import com.bank.contas.domain.services.AccountOperationService;
import com.bank.contas.domain.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AccountOperationServiceImpl implements AccountOperationService {

    private final AccountRepository accountRepository;

    private final AccountService accountService;

    private final AccountEventPublisher accountEventPublisher;

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
        accountEventPublisher.publisherStatementEvent(
                StatementEventDTO.toDTO(account, amount, TransactionType.DEPOSIT.toString()), TransactionType.DEPOSIT);
    }


    @Override
    public void withdraw(UUID accountId, BigDecimal amount) {
        var account = accountService.findAccountId(accountId);
        account.withdraw(amount);
        accountRepository.save(account);
        accountEventPublisher.publisherStatementEvent(StatementEventDTO.toDTO(
                account, amount, TransactionType.WITHDRAW.toString()), TransactionType.WITHDRAW);
    }

    @Override
    public void transfer(UUID accountIdOrigin, UUID accountIdDestiny, BigDecimal amount) {
        var accountOrigin = accountService.findAccountId(accountIdOrigin);
        var accountDestiny = accountService.findAccountId(accountIdDestiny);
        accountOrigin.transfer(accountDestiny, amount);

        accountRepository.save(accountOrigin);
        accountEventPublisher.publisherStatementEvent(StatementEventDTO.toDTO(
                accountOrigin, amount, TransactionType.TRANSFER.toString()), TransactionType.TRANSFER);

        accountRepository.save(accountDestiny);
        accountEventPublisher.publisherStatementEvent(StatementEventDTO.toDTO(
                accountDestiny, amount, TransactionType.DEPOSIT.toString()), TransactionType.DEPOSIT);

    }
}
