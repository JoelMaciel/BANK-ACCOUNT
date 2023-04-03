package com.bank.contas.api.controllers;

import com.bank.contas.api.dtos.request.AccountDTO;
import com.bank.contas.api.dtos.request.AccountDTOUpdate;
import com.bank.contas.api.dtos.response.AccountResponseDTO;
import com.bank.contas.domain.services.AccountService;
import com.bank.contas.infrastructure.specification.SpecificationTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AccountController {
    private final AccountService accountService;

    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    @GetMapping
    public Page<AccountResponseDTO> getAllAccounts(SpecificationTemplate.AccountSpec spec,
         @PageableDefault(page = 0, sort = "accountId",direction = Sort.Direction.ASC) Pageable pageable,
         @RequestParam(required = false) UUID userId) {
        return accountService.findAll(spec, userId, pageable);
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    @GetMapping("/{accountId}")
    public AccountResponseDTO getOneAccount(@PathVariable UUID accountId) {
        return accountService.findByAccount(accountId);
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponseDTO saveAccount(@RequestBody @Valid AccountDTO accountDTO,
                                          @RequestParam(value = "userId") UUID userId) {
       return accountService.save(accountDTO, userId);
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    @PatchMapping("{accountId}")
    public AccountResponseDTO updateAccount(@PathVariable UUID accountId,
                                            @RequestBody @Valid AccountDTOUpdate accountUpdate) {
      return   accountService.updateAccount(accountId, accountUpdate);
    }

}