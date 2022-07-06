package com.ead.authuser.domain.dtos;

import com.ead.authuser.domain.models.enums.CourseLevel;
import com.ead.authuser.domain.models.enums.CourseStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class CourseDTO {

    private UUID courseId;
    private String name;
    private String description;
    private String imageUrl;
    private CourseStatus courseStatus;
    private UUID userInstructor;
    private CourseLevel courseLevel;
}
