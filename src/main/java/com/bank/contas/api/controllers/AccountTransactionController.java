package com.bank.contas.api.controllers;

import com.bank.contas.domain.services.AccountOperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.util.annotation.Nullable;

import java.math.BigDecimal;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/transactions")
@RequiredArgsConstructor
@RestController
public class AccountTransactionController {

    private final AccountOperationService accountOperationService;

    @GetMapping("{accountId}/balance")
    public BigDecimal getBalance(@PathVariable UUID accountId) {
        return accountOperationService.consultBalance(accountId);
    }
    @PutMapping("/{accountId}/deposit")
    public void depositAccount(@PathVariable UUID accountId, @Nullable @RequestParam BigDecimal amount) {
        accountOperationService.deposit(accountId, amount);
    }

    @PutMapping("/{accountId}/withdraw")
    public void withdrawAccount(@PathVariable UUID accountId, @Nullable @RequestParam BigDecimal amount) {
        accountOperationService.withdraw(accountId, amount);
    }

    @PutMapping("/{accountIdOrigin}/{accountIdDestiny}/transfer")
    public void transferAccount(@PathVariable UUID accountIdOrigin,  @PathVariable UUID accountIdDestiny,
                                                                     @Nullable @RequestParam BigDecimal amount) {
        accountOperationService.transfer(accountIdOrigin, accountIdDestiny, amount);
    }
}
