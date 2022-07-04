package com.ead.authuser.controllers;

import com.ead.authuser.config.security.AuthenticationCurrentUserService;
import com.ead.authuser.config.security.UserDetailsImpl;
import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.ead.authuser.services.exceptions.ForbbidenUserException;
import com.ead.authuser.specifications.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationCurrentUserService authenticationCurrentUserService;


    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<UserModel>> findAllUsers(SpecificationTemplate.UserSpec spec, @PageableDefault(
            page = 0,
            size = 10,
            sort = "username",
            direction = Sort.Direction.ASC) Pageable pageable, Authentication authentication) {
        UserDetails userDetails = (UserDetailsImpl) authentication.getPrincipal();
        log.info("Authentication {}", userDetails.getUsername());
        Page<UserModel> usersPaged = userService.findAllUsers(pageable, spec);

        if (!usersPaged.isEmpty()) {
            for (UserModel user : usersPaged.toList()) {
                user.add(linkTo(methodOn(UserController.class).findById(user.getUserId())).withSelfRel());
            }
        }
        return ResponseEntity.ok().body(usersPaged);
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping(value = "/{userId}")
    public ResponseEntity<Object> findById(@PathVariable UUID userId) {
        UUID currentId = authenticationCurrentUserService.getCurrentUser().getUserId();
        if (currentId.equals(userId)){
            return ResponseEntity.ok().body(userService.findById(userId));
        }
        throw new ForbbidenUserException("Forbidden");
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        log.debug("DELETE deleteUser userId received {}", userId);
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{userId}")
    public ResponseEntity<UserModel> updateUser(@PathVariable UUID userId,
                                                @JsonView(UserDTO.UserView.UserPut.class)
                                                @RequestBody @Validated(UserDTO.UserView.UserPut.class) UserDTO userDTO) {
        log.debug("PUT updateUser userDTO received {}", userDTO.toString());
        return ResponseEntity.ok().body(userService.updateUser(userId, userDTO));
    }

    @PutMapping(value = "/{userId}/password")
    public ResponseEntity<Object> updatePassword(@PathVariable UUID userId,
                                                 @JsonView(UserDTO.UserView.PasswordPut.class)
                                                 @RequestBody @Validated(UserDTO.UserView.PasswordPut.class) UserDTO userDTO) {
        userService.updatePassword(userId, userDTO);
        return ResponseEntity.ok().body("Senha atualizada com sucesso.");
    }

    @PutMapping(value = "/{userId}/image")
    public ResponseEntity<UserModel> updateImage(@PathVariable UUID userId,
                                                 @JsonView(UserDTO.UserView.ImagePut.class)
                                                 @RequestBody @Validated(UserDTO.UserView.ImagePut.class) UserDTO userDTO) {
        return ResponseEntity.ok().body(userService.updateImage(userId, userDTO));
    }

}
