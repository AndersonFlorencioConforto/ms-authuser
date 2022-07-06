package com.ead.authuser.framework.adapters.in.controllers;

import com.ead.authuser.domain.dtos.InstructorDTO;
import com.ead.authuser.domain.models.UserModel;
import com.ead.authuser.application.port.in.UserServicePortIn;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/instructors")
public class InstructorController {

    @Autowired
    private UserServicePortIn userServicePortIn;


    @PostMapping("/subscription")
    public ResponseEntity<UserModel> saveSubscriptionInstructor(@RequestBody @Valid InstructorDTO instructorDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(userServicePortIn.saveSubscriptionInstructor(instructorDTO));
    }


}
