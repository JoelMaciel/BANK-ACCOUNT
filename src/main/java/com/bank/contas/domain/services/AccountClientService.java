package com.bank.contas.domain.services;

import com.bank.contas.api.models.response.AccountClientDTO;
import com.bank.contas.api.models.response.SubscriptionDTO;
import com.bank.contas.domain.models.Account;
import com.bank.contas.domain.models.AccountClient;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface AccountClientService {

    boolean existsByAccountAndClientId(Account account, UUID clientId);

    AccountClient save(AccountClient accountClient);

    //AccountClientDTO saveClientInAccount(UUID accountId, SubscriptionDTO subscriptionDTO);

    AccountClientDTO saveAndSubscriptionClientInAccount(UUID accountId, SubscriptionDTO subscriptionDTO);
}
