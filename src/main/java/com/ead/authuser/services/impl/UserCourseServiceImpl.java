package com.ead.authuser.services.impl;

import com.ead.authuser.repositories.UserCourseRespository;
import com.ead.authuser.services.UserCouserService;
import org.springframework.stereotype.Service;

@Service
public class UserCourseServiceImpl implements UserCouserService {

    private final UserCourseRespository userCourseRespository;

    public UserCourseServiceImpl(UserCourseRespository userCourseRespository) {
        this.userCourseRespository = userCourseRespository;
    }
}
