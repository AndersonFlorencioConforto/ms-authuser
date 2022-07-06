package com.ead.authuser.application.port.out;

import com.ead.authuser.domain.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryPortOut {

    Page<UserModel> findAll(Pageable pageable, Specification<UserModel> specification);
    Optional<UserModel> findById(UUID userId);
    UserModel save(UserModel userModel);
    void delete(UserModel userModel);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
