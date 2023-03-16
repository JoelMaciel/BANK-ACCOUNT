package com.bank.contas.api.models.request;

import com.bank.contas.domain.enums.TypeAccount;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class AccountDTO {

    @NotNull
    private String number;

    @NotNull
    private TypeAccount type;

    @NotNull
    private BigDecimal balance;

    @NotNull
    private String numberAgency;


}
