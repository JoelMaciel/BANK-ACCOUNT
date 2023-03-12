package com.bank.contas.api.models;

import com.bank.contas.domain.enums.TypeAccount;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class AccountDto {

    private UUID accountId;
    private UUID agencyId;
    private String number;
    private TypeAccount type;
    private BigDecimal balance;


}
