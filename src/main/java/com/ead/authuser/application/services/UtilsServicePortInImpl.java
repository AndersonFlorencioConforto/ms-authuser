package com.ead.authuser.application.services;

import com.ead.authuser.application.port.in.UtilsServicePortIn;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public class UtilsServicePortInImpl implements UtilsServicePortIn {

    @Value("${ead.api.url.course}")
    private String REQUEST_URL_COURSE;


    @Override
    public String createUrl(UUID userId, Pageable pageable) {
       return REQUEST_URL_COURSE + "/courses?userId=" + userId + "&page=" + pageable.getPageNumber()
                + "&size=" + pageable.getPageSize() + "&sort=" + pageable.getSort().toString()
                .replaceAll(": ", ",");
    }

    @Override
    public String deleteUserInCourse(UUID userId) {
        return REQUEST_URL_COURSE + "/courses/users/" + userId;
    }
}
