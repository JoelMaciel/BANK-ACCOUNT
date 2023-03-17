package com.bank.contas.api.controllers;

import com.bank.contas.api.clients.AccountRequestClient;
import com.bank.contas.api.models.response.ClientDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Log4j2
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class AccountClientController {

    private final AccountRequestClient accountRequestClient;

    @GetMapping("/accounts/{accountId}/clients")
    public Page<ClientDTO> getAllClientsByAccount(@PageableDefault(page = 0, size = 10, sort = "clientId",
            direction = Sort.Direction.ASC) Pageable pageable, @PathVariable(value = "accountId") UUID accountId){
        return accountRequestClient.getAllClientsByAccount(accountId, pageable);
    }
}
