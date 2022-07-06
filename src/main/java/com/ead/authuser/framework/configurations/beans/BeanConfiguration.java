package com.ead.authuser.framework.configurations.beans;

import com.ead.authuser.application.port.out.RoleRepositoryPortOut;
import com.ead.authuser.application.port.out.UserEventPublisherPortOut;
import com.ead.authuser.application.port.out.UserRepositoryPortOut;
import com.ead.authuser.application.services.RoleServicePortInImpl;
import com.ead.authuser.application.services.UserServicePortInImpl;
import com.ead.authuser.application.services.UtilsServicePortInImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfiguration {

    @Bean
    public RoleServicePortInImpl roleService(RoleRepositoryPortOut roleRepository) {
        return new RoleServicePortInImpl(roleRepository);
    }

    @Bean
    public UserServicePortInImpl userService(UserRepositoryPortOut userRepository, UserEventPublisherPortOut userEventPublisherPortOut, RoleRepositoryPortOut roleRepository, PasswordEncoder passwordEncoder) {
        return new UserServicePortInImpl(userRepository, userEventPublisherPortOut,roleRepository,passwordEncoder);
    }

    @Bean
    public UtilsServicePortInImpl utilsService() {
       return new UtilsServicePortInImpl();
    }
}
