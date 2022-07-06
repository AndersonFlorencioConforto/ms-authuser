package com.ead.authuser.framework.adapters.out.clients;

import com.ead.authuser.domain.dtos.CourseDTO;
import com.ead.authuser.domain.dtos.ResponsePageDTO;
import com.ead.authuser.application.port.in.UtilsServicePortIn;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class CourseClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UtilsServicePortIn utilsServicePortIn;


    //    @Retry(name = "retryInstance",fallbackMethod = "retryfallback")
    @CircuitBreaker(name = "circuitbreakerInstance")
    public Page<CourseDTO> getAllCoursesByUserId(UUID userId, Pageable pageable, String token) {
        List<CourseDTO> searchResult = null;
        ResponseEntity<ResponsePageDTO<CourseDTO>> result = null;
        String url = utilsServicePortIn.createUrl(userId, pageable);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> requestEntity = new HttpEntity<>("parameters", headers);
        log.debug("Request URL : {}", url);
        log.info("Request URL : {}", url);

        ParameterizedTypeReference<ResponsePageDTO<CourseDTO>> responseType = new ParameterizedTypeReference<ResponsePageDTO<CourseDTO>>() {
        };
        result = restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);
        searchResult = result.getBody().getContent();

        log.debug("Response Number of Elements: {}", searchResult.size());
        log.info("Ending request /courses userId {}", userId);
        return result.getBody();
    }

//    public Page<CourseDTO> circuitbreakerfallback(UUID userId, Pageable pageable, Throwable t) {
//        log.error("Inside circuit breaker fallback, cause - {}", t.toString());
//        List<CourseDTO> searchResult = new ArrayList<>();
//        return new PageImpl<>(searchResult);
//    }
//
//    public Page<CourseDTO> retryfallback(UUID userId, Pageable pageable, Throwable t) {
//        log.error("Inside retry fallback,cause - {}", t.toString());
//        List<CourseDTO> searchResult = new ArrayList<>();
//        return new PageImpl<>(searchResult);
//    }

}
