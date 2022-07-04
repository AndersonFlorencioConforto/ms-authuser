package com.ead.authuser.services;

import com.ead.authuser.dtos.InstructorDTO;
import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Page<UserModel> findAllUsers(Pageable pageable, Specification<UserModel> specification);
    Optional<UserModel> findById(UUID userId);
    void delete(UUID userId);
    UserModel save(UserDTO userDTO);
    UserModel updateUser(UUID userId, UserDTO userDTO);
    void updatePassword(UUID userId, UserDTO userDTO);
    UserModel updateImage(UUID userId, UserDTO userDTO);
    UserModel saveSubscriptionInstructor(InstructorDTO instructorDTO);
}
