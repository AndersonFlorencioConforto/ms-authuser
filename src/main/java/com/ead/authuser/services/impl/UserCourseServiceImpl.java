package com.ead.authuser.services.impl;

import com.ead.authuser.models.UserCourseModel;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.UserCourseRespository;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserCourseService;
import com.ead.authuser.services.exceptions.ConflictException;
import com.ead.authuser.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserCourseServiceImpl implements UserCourseService {

    private final UserCourseRespository userCourseRespository;
    private final UserRepository userRepository;


    public UserCourseServiceImpl(UserCourseRespository userCourseRespository, UserRepository userRepository) {
        this.userCourseRespository = userCourseRespository;
        this.userRepository = userRepository;
    }

    @Override
    public UserCourseModel save(UUID userId, UUID courseId) {
        Optional<UserModel> user = userRepository.findById(userId);
        UserModel userEntity = user.orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (userCourseRespository.existsByUserAndCourseId(userEntity, courseId)) {
            throw new ConflictException("Error: subscription already exists!");
        }
        return userCourseRespository.save(userEntity.convertToUserCourseModel(courseId));
    }

    @Transactional
    @Override
    public void delete(UUID courseId) {
        if (!userCourseRespository.existsByCourseId(courseId)) {
            throw new ResourceNotFoundException("UserCourse not found.");
        }
        userCourseRespository.deleteAllByCourseId(courseId);
    }
}
