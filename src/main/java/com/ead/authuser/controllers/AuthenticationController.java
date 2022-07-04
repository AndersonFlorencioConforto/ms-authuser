package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.RoleService;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping(value = "/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/signup")
    public ResponseEntity<UserModel> registerUser(@RequestBody @Validated(UserDTO.UserView.RegistrationPost.class) @JsonView(UserDTO.UserView.RegistrationPost.class) UserDTO userDTO) {
        log.debug("POST registerUser userDTO received {}",userDTO.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDTO));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Void> alterarRoleParaAdmin(@PathVariable(name = "userId") UUID userId){
        userService.alterarRoleParaAdmin(userId);
        log.info("Role alterada para admin");
        return ResponseEntity.noContent().build();
    }
}
