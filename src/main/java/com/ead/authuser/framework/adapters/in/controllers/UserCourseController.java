package com.ead.authuser.framework.adapters.in.controllers;

import com.ead.authuser.framework.adapters.out.clients.CourseClient;
import com.ead.authuser.domain.dtos.CourseDTO;
import com.ead.authuser.application.port.in.UserServicePortIn;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCourseController {

    @Autowired
    private CourseClient courseClient;

    @Autowired
    private UserServicePortIn userServicePortIn;

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("/users/{userId}/courses")
    public ResponseEntity<Page<CourseDTO>> getAllCoursesByUser(@PageableDefault(
            page = 0,
            size = 10,
            sort = "courseId",
            direction = Sort.Direction.ASC) Pageable pageable, @PathVariable(value = "userId") UUID userId,
                                                               @RequestHeader("Authorization") String token) {
        userServicePortIn.findById(userId);
        return ResponseEntity.ok().body(courseClient.getAllCoursesByUserId(userId, pageable,token));
    }
}
