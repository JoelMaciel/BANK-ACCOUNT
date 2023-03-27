package com.bank.contas.api.dtos.request;

import com.bank.contas.domain.enums.TypeAccount;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AccountDTOUpdate {

    @NotNull
    private String number;

    @NotNull
    private TypeAccount type;

}
