package com.bank.contas.api.models;

import com.bank.contas.domain.enums.TypeAccount;
import com.bank.contas.domain.models.Agency;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class AccountDto {

    private UUID accountId;
    private String number;
    private BigDecimal balance;
    private TypeAccount type;
    private AgencyDto agency;

}
