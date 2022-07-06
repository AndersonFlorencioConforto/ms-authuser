package com.ead.authuser.framework.adapters.in.controllers;

import com.ead.authuser.framework.security.JwtProvider;
import com.ead.authuser.domain.dtos.JwtDTO;
import com.ead.authuser.domain.dtos.LoginDTO;
import com.ead.authuser.domain.dtos.UserDTO;
import com.ead.authuser.domain.models.UserModel;
import com.ead.authuser.application.port.in.UserServicePortIn;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/auth")
public class AuthenticationController {

    @Autowired
    private UserServicePortIn userServicePortIn;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(value = "/signup")
    public ResponseEntity<UserModel> registerUser(@RequestBody @Validated(UserDTO.UserView.RegistrationPost.class) @JsonView(UserDTO.UserView.RegistrationPost.class) UserDTO userDTO) {
        log.debug("POST registerUser userDTO received {}", userDTO.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(userServicePortIn.save(userDTO));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<JwtDTO> authenticateUser(@RequestBody @Valid LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generatJwt(authentication);
        return ResponseEntity.ok().body(new JwtDTO(jwt));
    }
}
