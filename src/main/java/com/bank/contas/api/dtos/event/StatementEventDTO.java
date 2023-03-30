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
public class StatementEventDTO {

    @Id
    private UUID statementId;
    private UUID accountId;
    private String number;
    private String type;
    private BigDecimal amount;
    private String transaction;

    public static StatementEventDTO toDTO(AccountModel accountModel, BigDecimal amount, String transaction) {
        return StatementEventDTO.builder().amount(amount)
                .statementId(UUID.randomUUID())
                .accountId(accountModel.getAccountId())
                .number(accountModel.getNumber())
                .type(accountModel.getType().toString())
                .transaction(transaction)
                .build();
    }

}
