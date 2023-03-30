package com.bank.contas.api.publishers;

import com.bank.contas.api.dtos.event.StatementEventDTO;
import com.bank.contas.domain.enums.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AccountEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value(value = "${bank.broker.exchange.accountEvent}")
    private String exchangeAccountEvent;

    public void publisherStatementEvent(StatementEventDTO statementEventDTO, TransactionType transactionType) {
        statementEventDTO.setTransaction(transactionType.toString());
        rabbitTemplate.convertAndSend(exchangeAccountEvent, "", statementEventDTO);

    }
}
