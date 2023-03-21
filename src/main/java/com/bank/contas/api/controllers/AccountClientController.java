package com.bank.contas.api.controllers;

import com.bank.contas.api.clients.ClientRequestClient;
import com.bank.contas.api.models.response.AccountClientDTO;
import com.bank.contas.api.models.response.ClientDTO;
import com.bank.contas.api.models.response.SubscriptionDTO;
import com.bank.contas.domain.services.AccountClientService;
import com.bank.contas.domain.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping
@CrossOrigin(origins = "*", maxAge = 3600)
public class AccountClientController {
    private final ClientRequestClient clientRequestClient;
    private final AccountClientService accountClientService;

    private final AccountService accountService;

    @GetMapping("/accounts/{accountId}/clients")
    public Page<ClientDTO> getAllClientsByAccount(@PageableDefault(page = 0, size = 10, sort = "clientId",
            direction = Sort.Direction.ASC) Pageable pageable, @PathVariable(value = "accountId") UUID accountId){
        accountService.searchOrFail(accountId);
        return clientRequestClient.getAllClientsByAccount(accountId, pageable);
    }

    @PostMapping("/accounts/{accountId}/clients/subscription")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountClientDTO saveSubscriptionClientInAccount(
            @PathVariable(value = "accountId") UUID accountId, @RequestBody @Valid SubscriptionDTO subscriptionDTO) {
        return accountClientService.saveAndSubscriptionClientInAccount(accountId, subscriptionDTO);
    }

}
