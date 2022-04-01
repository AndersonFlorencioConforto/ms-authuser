package com.ead.authuser.services.impl;

import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<UserModel> findAllUsers(Pageable pageable, Specification<UserModel> specification) {
        return userRepository.findAll(specification,pageable);
    }

    @Override
    public Optional<UserModel> findById(UUID userId) {
        return userRepository.findById(userId);
    }

    @Override
    public void delete(UserModel userModel) {
        userRepository.delete(userModel);
    }

    @Override
    public UserModel save(UserDTO userDTO) {
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDTO,userModel);
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.STUDENT);
        return userRepository.save(userModel);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void updateUser(UserModel userModel, UserDTO userDTO) {
        userModel.setFullName(userDTO.getFullName());
        userModel.setPhoneNumber(userDTO.getPhoneNumber());
        userRepository.save(userModel);
    }

    @Override
    public void updatePassword(UserModel userModel, UserDTO userDTO) {
        userModel.setPassword(userDTO.getPassword());
        userRepository.save(userModel);

    }

    @Override
    public void updateImage(UserModel userModel, UserDTO userDTO) {
        userModel.setImageUrl(userDTO.getImageUrl());
        userRepository.save(userModel);
    }

}
