package com.bank.contas.api.models.input;

import com.bank.contas.api.models.AgencyDto;
import com.bank.contas.domain.enums.TypeAccount;
import com.bank.contas.domain.models.Agency;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class AccountInput {

    @NotNull
    private String number;

    @NotNull
    private BigDecimal balance;

    @NotNull
    private TypeAccount type;

    @NotNull
    private UUID agencyId;

}
