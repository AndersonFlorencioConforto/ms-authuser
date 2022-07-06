package com.ead.authuser.application.port.out;

import com.ead.authuser.domain.dtos.UserEventPublisherDTO;
import com.ead.authuser.domain.models.enums.ActionType;

public interface UserEventPublisherPortOut {

    void publishUserEvent(UserEventPublisherDTO userEventPublisherDTO, ActionType actionType);
}
