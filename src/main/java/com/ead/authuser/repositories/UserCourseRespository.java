package com.ead.authuser.repositories;

import com.ead.authuser.models.UserCourseModel;
import com.ead.authuser.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserCourseRespository extends JpaRepository<UserCourseModel, UUID> {

    boolean existsByUserAndCourseId(UserModel userModel,UUID courseId);
    @Query(nativeQuery = true,value = "SELECT * FROM tb_users_courses WHERE user_user_id = :userId")
    List<UserCourseModel> findAllUserCourseIntoUser(@Param("userId") UUID userId);
}
