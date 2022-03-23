package com.ead.authuser.services;

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
    void delete(UserModel userModel);
    UserModel save(UserDTO userDTO);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    void updateUser(UserModel userModel, UserDTO userDTO);
    void updatePassword(UserModel userModel, UserDTO userDTO);
    void updateImage(UserModel userModel, UserDTO userDTO);
}
