package com.bank.contas.api.consumers;

import com.bank.contas.api.models.event.UserEventDTO;
import com.bank.contas.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserConsumer {

    private final UserService userService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${bank.broker.queue.userEventQueue.name}", durable = "true"),
            exchange = @Exchange(value = "${bank.broker.exchange.userEventExchange}",
                                 type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true")
    ))
    public void listenUserEvent(@Payload UserEventDTO userEventDTO) {
        var userModel = UserEventDTO.toEntity(userEventDTO);

        switch (userEventDTO.getActionType()) {
            case "CREATE", "UPDATE"  -> userService.save(userModel);
            case "DELETE" -> userService.delete(userEventDTO.getUserId());
        }
    }
}
