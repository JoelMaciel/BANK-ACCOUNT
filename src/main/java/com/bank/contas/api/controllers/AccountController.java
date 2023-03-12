package com.bank.contas.api.controllers;

import com.bank.contas.api.models.AccountDto;
import com.bank.contas.api.models.converter.AccountInputToDomain;
import com.bank.contas.api.models.converter.AccountInputUpdateToDomain;
import com.bank.contas.api.models.converter.AccountToDto;
import com.bank.contas.api.models.input.AccountInput;
import com.bank.contas.api.models.input.AccountInputUpdate;
import com.bank.contas.domain.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountInputToDomain accountInputToDomain;

    @Autowired
    private AccountInputUpdateToDomain accountInputUpdateToDomain;

    @Autowired
    private AccountToDto accountToDto;

    @GetMapping
    public Page<AccountDto> getAllAccounts(@PageableDefault(page = 0, sort = "accountId",
            direction = Sort.Direction.ASC) Pageable pageable) {
        var accountPage = accountService.findAll(pageable);
        return  accountPage;
    }

    @GetMapping("/{accountId}")
    public AccountDto getOneAccount(@PathVariable UUID accountId) {
        var accountCurrent = accountService.searchOrFail(accountId);
        var accountDto = accountToDto.converter(accountCurrent);
        return accountDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto saveAccount(@RequestBody @Valid  AccountInput accountInput) {

      var account =  accountInputToDomain.toDomainObject(accountInput);
      account.setCreationDate(OffsetDateTime.now());
      account.setUpdateDate(OffsetDateTime.now());
      accountService.save(account);

      var accountDto = accountToDto.converter(account);

      return accountDto;
    }

    @PutMapping("{accountId}")
    public AccountDto updateAccount(@PathVariable UUID accountId,
                                    @RequestBody @Valid AccountInputUpdate accountUpdate) {
        var accountCurrente = accountService.searchOrFail(accountId);

        accountInputUpdateToDomain.copyToDomainObject(accountUpdate, accountCurrente);
        accountCurrente.setUpdateDate(OffsetDateTime.now());
        accountService.save(accountCurrente);

        var accountDto = accountToDto.converter(accountCurrente);

        return accountDto;

    }

    @DeleteMapping("/{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable UUID accountId) {
        accountService.delete(accountId);
    }
}
