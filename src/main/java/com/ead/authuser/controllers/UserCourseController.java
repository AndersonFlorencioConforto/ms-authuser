package com.ead.authuser.controllers;

import com.ead.authuser.clients.CourseClient;
import com.ead.authuser.dtos.CourseDTO;
import com.ead.authuser.dtos.UserCourseDTO;
import com.ead.authuser.models.UserCourseModel;
import com.ead.authuser.services.UserCourseService;
import com.ead.authuser.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCourseController {

    @Autowired
    private CourseClient courseClient;

    @Autowired
    private UserCourseService userCourseService;

    @Autowired
    private UserService userService;

    @GetMapping("/users/{userId}/courses")
    public ResponseEntity<Page<CourseDTO>> getAllCoursesByUser(@PageableDefault(
            page = 0,
            size = 10,
            sort = "courseId",
            direction = Sort.Direction.ASC) Pageable pageable, @PathVariable(value = "userId") UUID userId) {
        userService.findById(userId);
        return ResponseEntity.ok().body(courseClient.getAllCoursesByUserId(userId, pageable));
    }

    @PostMapping("/users/{userId}/courses/subscription")
    public ResponseEntity<UserCourseModel> saveSubscriptionUserInCourse(@PathVariable(value = "userId") UUID userId, @RequestBody @Valid UserCourseDTO userCourseDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body( userCourseService.save(userId, userCourseDTO.getCourseId()));
    }

    @DeleteMapping("/users/courses/{courseId}")
    public ResponseEntity<Void> deleteUserCourseByCourseId(@PathVariable(value = "courseId") UUID courseId){
        userCourseService.delete(courseId);
        return ResponseEntity.noContent().build();
    }
}
