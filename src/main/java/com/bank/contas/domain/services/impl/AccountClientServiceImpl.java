package com.bank.contas.domain.services.impl;

import com.bank.contas.api.clients.ClientRequestClient;
import com.bank.contas.api.models.response.AccountClientDTO;
import com.bank.contas.api.models.response.ClientDTO;
import com.bank.contas.api.models.response.SubscriptionDTO;
import com.bank.contas.domain.exceptions.EntityInUseException;
import com.bank.contas.domain.exceptions.EntityNotExistsException;
import com.bank.contas.domain.models.Account;
import com.bank.contas.domain.models.AccountClient;
import com.bank.contas.domain.repositories.AccountClientRepository;
import com.bank.contas.domain.services.AccountClientService;
import com.bank.contas.domain.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AccountClientServiceImpl implements AccountClientService {

    private final AccountClientRepository accountClientRepository;
    private final AccountService accountService;

    private final ClientRequestClient clientRequestClient;

//    @Override
//    public AccountClientDTO saveClientInAccount(UUID accountId, SubscriptionDTO subscriptionDTO) {
//        var account = accountService.searchOrFail(accountId);
//        ResponseEntity<ClientDTO> responseClient;
//
//        if(existsByAccountAndClientId(account, subscriptionDTO.getClientId())){
//            throw new EntityInUseException("Error: There is already a customer registered in this account!");
//        }
//        try {
//            responseClient = clientRequestClient.getOneClientById(subscriptionDTO.getClientId());
//        } catch (HttpStatusCodeException e) {
//            throw new EntityNotExistsException("Client not found");
//        }
//        return  AccountClientDTO.converterToDTO(save(account.converterToAccountClient(subscriptionDTO.getClientId())));
//    }

    @Transactional
    @Override
    public AccountClientDTO saveAndSubscriptionClientInAccount(UUID accountId, SubscriptionDTO subscriptionDTO) {
        var account = accountService.searchOrFail(accountId);
        ResponseEntity<ClientDTO> responseClient;
        if(existsByAccountAndClientId(account, subscriptionDTO.getClientId())){
            throw new EntityInUseException("Error: There is already a customer registered in this account!");
        }
        try {
            responseClient = clientRequestClient.getOneClientById(subscriptionDTO.getClientId());
        } catch (HttpStatusCodeException e) {
            throw new EntityNotExistsException("Client not found");
        }
        var accountClient = save(account.converterToAccountClient(subscriptionDTO.getClientId()));
        clientRequestClient.postSubscriptionClientInAccount(accountClient.getAccount().getAccountId(), accountClient.getClientId());
        return  AccountClientDTO.converterToDTO(accountClient);
    }

    @Override
    public boolean existsByAccountAndClientId(Account account, UUID clientId) {
        return accountClientRepository.existsByAccountAndClientId(account, clientId);
    }

    @Override
    public AccountClient save(AccountClient accountClient) {
        return accountClientRepository.save(accountClient);
    }


}
