package com.ead.authuser.publishers;

import com.ead.authuser.dtos.UserEventPublisherDTO;
import com.ead.authuser.enums.ActionType;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserEventPublisher {

    @Value(value="${ead.broker.exchange.userEvent}")
    private String exchangeUserEvent;

    private final RabbitTemplate rabbitTemplate;

    public UserEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishUserEvent(UserEventPublisherDTO userEventPublisherDTO, ActionType actionType) {
        userEventPublisherDTO.setActionType(actionType.toString());
        rabbitTemplate.convertAndSend(exchangeUserEvent,"",userEventPublisherDTO);
    }
}
