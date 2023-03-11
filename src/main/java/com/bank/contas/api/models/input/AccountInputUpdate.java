package com.bank.contas.api.models.input;

import com.bank.contas.domain.enums.TypeAccount;
import com.bank.contas.domain.models.Agency;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class AccountInputUpdate {

    @NotNull
    private String number;

    @NotNull
    private TypeAccount type;

    @NotNull
    private Agency agency;

}
