package com.bank.contas.api.models;

import com.bank.contas.domain.enums.TypeAccount;
import com.bank.contas.domain.models.Agency;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class AgencyDto {

    private UUID agencyId;
    private String name;
    private String number;

}
