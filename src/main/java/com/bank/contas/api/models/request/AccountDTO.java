package com.bank.contas.api.models.request;

import com.bank.contas.domain.enums.TypeAccount;
import com.bank.contas.domain.models.AccountModel;
import com.bank.contas.domain.models.AgencyModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    @NotNull
    private String number;

    @NotNull
    private TypeAccount type;

    @NotNull
    private BigDecimal balance;

    @NotNull
    private String numberAgency;

    public static AccountModel toEntity(AccountDTO accountDTO, AgencyModel agencyModel) {
        return AccountModel.builder()
                .number(accountDTO.getNumber())
                .type(accountDTO.getType())
                .balance(accountDTO.getBalance())
                .agency(agencyModel)
                .build();
    }

}
