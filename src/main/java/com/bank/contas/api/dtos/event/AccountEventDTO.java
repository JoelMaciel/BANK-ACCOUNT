package com.bank.contas.api.dtos.event;

import com.bank.contas.domain.models.AccountModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AccountEventDTO {

    @Id
    private UUID accountId;
    private String number;
    private String type;
    private BigDecimal amount;
    private String transaction;

    public static AccountEventDTO toDTO(AccountModel accountModel, BigDecimal amount) {
        return AccountEventDTO.builder().amount(amount)
                .accountId(accountModel.getAccountId())
                .number(accountModel.getNumber())
                .type(accountModel.getType().toString())
                .build();
    }

}
