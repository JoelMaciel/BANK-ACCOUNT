package com.bank.contas.api.dtos.response;

import com.bank.contas.domain.enums.TypeAccount;
import com.bank.contas.domain.models.AccountModel;
import com.bank.contas.domain.models.UserModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDTO {

    @JsonIgnore
    private UUID accountId;

    private String number;

    private TypeAccount type;

    private BigDecimal balance;

    private String agencyNumber;

    private UserModel user;

    public static AccountResponseDTO toDTO(AccountModel accountModel) {
        return AccountResponseDTO.builder()
                .accountId(accountModel.getAccountId())
                .number(accountModel.getNumber())
                .type(accountModel.getType())
                .balance(accountModel.getBalance())
                .agencyNumber(accountModel.getAgency().getNumber())
                .user(accountModel.getUser())
                .build();
    }
}
