package com.ead.authuser.application.port.in;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UtilsServicePortIn {

    String createUrl(UUID userId, Pageable pageable);

    String deleteUserInCourse(UUID userId);
}
