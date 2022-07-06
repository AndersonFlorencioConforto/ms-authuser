package com.ead.authuser.application.services;

import com.ead.authuser.application.port.out.RoleRepositoryPortOut;
import com.ead.authuser.application.port.out.UserEventPublisherPortOut;
import com.ead.authuser.application.port.out.UserRepositoryPortOut;
import com.ead.authuser.domain.dtos.InstructorDTO;
import com.ead.authuser.domain.dtos.UserDTO;
import com.ead.authuser.domain.models.enums.ActionType;
import com.ead.authuser.domain.models.enums.RoleType;
import com.ead.authuser.domain.models.enums.UserStatus;
import com.ead.authuser.domain.models.enums.UserType;
import com.ead.authuser.domain.models.RoleModel;
import com.ead.authuser.domain.models.UserModel;
import com.ead.authuser.application.port.in.UserServicePortIn;
import com.ead.authuser.domain.exceptions.ConflictException;
import com.ead.authuser.domain.exceptions.ResourceNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.UUID;

@Log4j2
public class UserServicePortInImpl implements UserServicePortIn {

    private final UserRepositoryPortOut userRepository;
    private final UserEventPublisherPortOut userEventPublisherPortOut;
    private final RoleRepositoryPortOut roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServicePortInImpl(UserRepositoryPortOut userRepository, UserEventPublisherPortOut userEventPublisherPortOut, RoleRepositoryPortOut roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userEventPublisherPortOut = userEventPublisherPortOut;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Page<UserModel> findAllUsers(Pageable pageable, Specification<UserModel> specification) {
        return userRepository.findAll(pageable, specification);
    }

    @Override
    public Optional<UserModel> findById(UUID userId) {
        Optional<UserModel> userOptional = userRepository.findById(userId);
        userOptional.orElseThrow(() -> new ResourceNotFoundException("User not found."));
        return userOptional;
    }

    @Transactional
    @Override
    public void delete(UUID userId) {
        Optional<UserModel> userOptional = userRepository.findById(userId);
        UserModel userModel = userOptional.orElseThrow(() -> new ResourceNotFoundException("User not found."));
        userRepository.delete(userModel);
        userEventPublisherPortOut.publishUserEvent(userModel.ConvertUserEventPublisherDTO(), ActionType.DELETE);
    }

    @Transactional
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
        RoleModel role = roleRepository.findByRoleName(RoleType.ROLE_STUDENT).orElseThrow(() -> new ResourceNotFoundException("Role is not found"));
        var userModel = new UserModel();
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        BeanUtils.copyProperties(userDTO, userModel);
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.STUDENT);
        userModel.getRoles().add(role);
        userModel = userRepository.save(userModel);
        userEventPublisherPortOut.publishUserEvent(userModel.ConvertUserEventPublisherDTO(), ActionType.CREATE);
        return userModel;
    }

    @Transactional
    @Override
    public UserModel updateUser(UUID userId, UserDTO userDTO) {
        Optional<UserModel> userOptional = userRepository.findById(userId);
        UserModel userModel = userOptional.orElseThrow(() -> new ResourceNotFoundException("User not found."));
        userModel.setFullName(userDTO.getFullName());
        userModel.setPhoneNumber(userDTO.getPhoneNumber());
        userModel = userRepository.save(userModel);
        userEventPublisherPortOut.publishUserEvent(userModel.ConvertUserEventPublisherDTO(), ActionType.UPDATE);
        return userModel;
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
        userModel = userRepository.save(userModel);
        userEventPublisherPortOut.publishUserEvent(userModel.ConvertUserEventPublisherDTO(), ActionType.UPDATE);
        return userModel;
    }

    @Transactional
    @Override
    public UserModel saveSubscriptionInstructor(InstructorDTO instructorDTO) {
        Optional<UserModel> userOptional = userRepository.findById(instructorDTO.getUserId());
        RoleModel role = roleRepository.findByRoleName(RoleType.ROLE_INSTRUCTOR).orElseThrow(() -> new ResourceNotFoundException("Role is not found"));
        UserModel userModel = userOptional.orElseThrow(() -> new ResourceNotFoundException("User not found."));
        userModel.setUserType(UserType.INSTRUCTOR);
        userModel.getRoles().add(role);
        userModel = userRepository.save(userModel);
        userEventPublisherPortOut.publishUserEvent(userModel.ConvertUserEventPublisherDTO(), ActionType.UPDATE);
        return userModel;
    }


}
