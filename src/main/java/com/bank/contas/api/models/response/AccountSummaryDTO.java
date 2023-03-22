package com.bank.contas.api.models.response;

import com.bank.contas.domain.enums.TypeAccount;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AccountSummaryDTO {

    private String number;

    private TypeAccount type;

    private BigDecimal balance;

    private UUID agencyId;
}
