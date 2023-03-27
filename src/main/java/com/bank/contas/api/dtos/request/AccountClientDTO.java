package com.bank.contas.api.dtos.request;

import lombok.Data;

import java.util.UUID;

@Data
public class AccountClientDTO {

    private UUID accountId;
    private UUID clientId;

}
