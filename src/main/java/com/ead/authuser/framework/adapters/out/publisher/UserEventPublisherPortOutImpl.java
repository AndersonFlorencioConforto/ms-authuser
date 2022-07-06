package com.ead.authuser.framework.adapters.out.publisher;

import com.ead.authuser.application.port.out.UserEventPublisherPortOut;
import com.ead.authuser.domain.dtos.UserEventPublisherDTO;
import com.ead.authuser.domain.models.enums.ActionType;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserEventPublisherPortOutImpl implements UserEventPublisherPortOut {

    @Value(value="${ead.broker.exchange.userEvent}")
    private String exchangeUserEvent;

    private final RabbitTemplate rabbitTemplate;

    public UserEventPublisherPortOutImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishUserEvent(UserEventPublisherDTO userEventPublisherDTO, ActionType actionType) {
        userEventPublisherDTO.setActionType(actionType.toString());
        rabbitTemplate.convertAndSend(exchangeUserEvent,"",userEventPublisherDTO);
    }
}
