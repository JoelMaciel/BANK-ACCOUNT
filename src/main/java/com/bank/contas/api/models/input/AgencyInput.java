package com.bank.contas.api.models.input;

import com.bank.contas.domain.enums.TypeAccount;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class AgencyInput {

    @NotBlank
    private String name;

    @NotBlank
    private String number;

}
