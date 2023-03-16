package com.bank.contas.api.controllers;

import com.bank.contas.api.models.request.AccountDTO;
import com.bank.contas.api.models.request.AccountDTOUpdate;
import com.bank.contas.api.models.response.AccountSummaryDTO;
import com.bank.contas.domain.services.AccountService;
import com.bank.contas.infrastructure.specification.SpecificationTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AccountController {
    private final AccountService accountService;

    @GetMapping
    public Page<AccountSummaryDTO> getAllAccounts(@PageableDefault(page = 0, sort = "accountId",
            direction = Sort.Direction.ASC) Pageable pageable) {
        return accountService.findAll(pageable);
    }

    @GetMapping("/{accountId}")
    public AccountSummaryDTO getOneAccount(@PathVariable UUID accountId) {
        return accountService.findByAccount(accountId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDTO saveAccount(@RequestBody @Valid AccountDTO accountDTO) {
       return accountService.save(accountDTO);
    }

    @PutMapping("{accountId}")
    public AccountSummaryDTO updateAccount(@PathVariable UUID accountId,
                          @RequestBody @Valid AccountDTOUpdate accountUpdate) {
      return   accountService.updateAccount(accountId, accountUpdate);
    }

    @DeleteMapping("/{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable UUID accountId) {
        accountService.delete(accountId);
    }
}