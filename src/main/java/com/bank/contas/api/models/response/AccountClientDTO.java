package com.bank.contas.api.models.response;

import com.bank.contas.domain.models.Account;
import com.bank.contas.domain.models.AccountClient;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AccountClientDTO {

    private UUID id;
    private UUID clientId;
    private Account account;

    public static AccountClientDTO converterToDTO(AccountClient accountClient) {
        return AccountClientDTO.builder()
                .id(accountClient.getId())
                .clientId(accountClient.getClientId())
                .account(accountClient.getAccount())
                .build();
    }

}
