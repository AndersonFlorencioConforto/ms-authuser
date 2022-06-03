package com.ead.authuser.clients;

import com.ead.authuser.dtos.CourseDTO;
import com.ead.authuser.dtos.ResponsePageDTO;
import com.ead.authuser.services.UtilsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class CourseClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UtilsService utilsService;


    public Page<CourseDTO> getAllCoursesByUserId(UUID userId, Pageable pageable) {
        List<CourseDTO> searchResult = null;
        ResponseEntity<ResponsePageDTO<CourseDTO>> result = null;
        String url = utilsService.createUrl(userId,pageable);
        log.debug("Request URL : {}" , url);
        log.info("Request URL : {}" , url);
        try {
            ParameterizedTypeReference<ResponsePageDTO<CourseDTO>> responseType = new ParameterizedTypeReference<ResponsePageDTO<CourseDTO>>() {};
            result = restTemplate.exchange(url, HttpMethod.GET,null,responseType);
            searchResult = result.getBody().getContent();
            log.debug("Response Number of Elements: {}" , searchResult.size());
        }catch (HttpStatusCodeException e) {
            log.error("Error request /courses {}", e);
        }
        log.info("Ending request /courses userId {}" , userId);
        return result.getBody();
    }
}
