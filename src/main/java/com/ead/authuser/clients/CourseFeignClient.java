package com.ead.authuser.clients;

import com.ead.authuser.dtos.CourseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.UUID;

@FeignClient(value = "ms-courses",url = "http://localhost:8082")
public interface CourseFeignClient {

    @GetMapping(value = "/courses")
    ResponseEntity<Page<CourseDTO>> findAllCoursesByUserId(Pageable pageable, @RequestParam(required = false) UUID userId);
}
