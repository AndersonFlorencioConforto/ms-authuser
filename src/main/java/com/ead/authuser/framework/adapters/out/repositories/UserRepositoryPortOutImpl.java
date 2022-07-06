package com.ead.authuser.framework.adapters.out.repositories;

import com.ead.authuser.application.port.out.UserRepositoryPortOut;
import com.ead.authuser.domain.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserRepositoryPortOutImpl implements UserRepositoryPortOut {

    private final UserRepository userRepository;

    public UserRepositoryPortOutImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<UserModel> findAll(Pageable pageable, Specification<UserModel> specification) {
        return userRepository.findAll(specification,pageable);
    }

    @Override
    public Optional<UserModel> findById(UUID userId) {
        return userRepository.findById(userId);
    }

    @Override
    public UserModel save(UserModel userModel) {
        return userRepository.save(userModel);
    }

    @Override
    public void delete(UserModel userModel) {
        userRepository.delete(userModel);
    }

    @Override
    public boolean existsByUsername(String username) {
      return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
