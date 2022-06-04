package com.ead.authuser.services.impl;

import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserService;
import com.ead.authuser.services.exceptions.ConflictException;
import com.ead.authuser.services.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<UserModel> findAllUsers(Pageable pageable, Specification<UserModel> specification) {
        return userRepository.findAll(specification, pageable);
    }

    @Override
    public Optional<UserModel> findById(UUID userId) {
        Optional<UserModel> userOptional = userRepository.findById(userId);
        userOptional.orElseThrow(() -> new ResourceNotFoundException("User not found."));
        return userOptional;
    }

    @Override
    public void delete(UUID userId) {
        Optional<UserModel> userOptional = userRepository.findById(userId);
        userRepository.delete(userOptional.orElseThrow(() -> new ResourceNotFoundException("User not found.")));
    }

    @Override
    public UserModel save(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            log.warn("Username {} is already taken! ", userDTO.getUsername());
            throw new ConflictException("Error: Username is already taken!");
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            log.warn("Email {} is already taken! ", userDTO.getEmail());
            throw new ConflictException("Error: Email is already taken!");
        }
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDTO, userModel);
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.STUDENT);
        return userRepository.save(userModel);
    }

    @Override
    public UserModel updateUser(UUID userId, UserDTO userDTO) {
        Optional<UserModel> userOptional = userRepository.findById(userId);
        UserModel userModel = userOptional.orElseThrow(() -> new ResourceNotFoundException("User not found."));
        userModel.setFullName(userDTO.getFullName());
        userModel.setPhoneNumber(userDTO.getPhoneNumber());
        return userRepository.save(userModel);
    }

    @Override
    public void updatePassword(UUID userId, UserDTO userDTO) {
        Optional<UserModel> userOptional = userRepository.findById(userId);
        UserModel userModel = userOptional.orElseThrow(() -> new ResourceNotFoundException("User not found."));
        if (!userDTO.getOldPassword().equals(userOptional.get().getPassword())) {
            throw new ConflictException("Error: Mismatched old password");
        }
        userModel.setPassword(userDTO.getPassword());
        userRepository.save(userModel);

    }

    @Override
    public UserModel updateImage(UUID userId, UserDTO userDTO) {
        Optional<UserModel> userOptional = userRepository.findById(userId);
        UserModel userModel = userOptional.orElseThrow(() -> new ResourceNotFoundException("User not found."));
        userModel.setImageUrl(userDTO.getImageUrl());
        return userRepository.save(userModel);
    }

}
