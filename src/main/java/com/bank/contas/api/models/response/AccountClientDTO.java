package com.bank.contas.api.models.response;

import com.bank.contas.domain.models.AccountModel;
import com.bank.contas.domain.models.UserModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AccountClientDTO {

    @JsonIgnore
    private UUID id;
    private UUID clientId;
    private AccountModel accountModel;

}
