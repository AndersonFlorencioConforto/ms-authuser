package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.ead.authuser.specifications.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.UUID;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserModel>> findAllUsers(SpecificationTemplate.UserSpec spec, @PageableDefault(
            page = 0,
            size = 10,
            sort = "username",
            direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(required = false) UUID courseId) {
        Page<UserModel> usersPaged = null;
        if (courseId != null){
            usersPaged = userService.findAllUsers(pageable,SpecificationTemplate.userCourseId(courseId).and(spec));
        } else {
            usersPaged = userService.findAllUsers(pageable,spec);
        }

        if(!usersPaged.isEmpty()) {
            for (UserModel user: usersPaged.toList()) {
                user.add(linkTo(methodOn(UserController.class).findById(user.getUserId())).withSelfRel());
            }
        }
        return ResponseEntity.ok().body(usersPaged);
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<Object> findById(@PathVariable UUID userId) {
        Optional<UserModel> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok().body(userOptional.get());
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable UUID userId) {
        log.debug("DELETE deleteUser userId received {}",userId);
        Optional<UserModel> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        userService.delete(userOptional.get());
        return ResponseEntity.ok().body("User deleted success");
    }

    @PutMapping(value = "/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable UUID userId,
                                             @JsonView(UserDTO.UserView.UserPut.class)
                                             @RequestBody @Validated(UserDTO.UserView.UserPut.class) UserDTO userDTO) {
        log.debug("PUT updateUser userDTO received {}",userDTO.toString());
        Optional<UserModel> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        userService.updateUser(userOptional.get(),userDTO);
        return ResponseEntity.ok().body(userOptional);
    }

    @PutMapping(value = "/{userId}/password")
    public ResponseEntity<Object> updatePassword(@PathVariable UUID userId,
                                                 @JsonView(UserDTO.UserView.PasswordPut.class)
                                                 @RequestBody @Validated(UserDTO.UserView.PasswordPut.class) UserDTO userDTO) {
        Optional<UserModel> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        if (!userDTO.getOldPassword().equals(userOptional.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Mismatched old password");
        }

        userService.updatePassword(userOptional.get(),userDTO);
        return ResponseEntity.ok().body("Senha atualizada com sucesso.");
    }

    @PutMapping(value = "/{userId}/image")
    public ResponseEntity<Object> updateImage(@PathVariable UUID userId,
                                              @JsonView(UserDTO.UserView.ImagePut.class)
                                              @RequestBody @Validated(UserDTO.UserView.ImagePut.class) UserDTO userDTO) {
        Optional<UserModel> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        userService.updateImage(userOptional.get(),userDTO);
        return ResponseEntity.ok().body(userOptional.get());
    }

}
