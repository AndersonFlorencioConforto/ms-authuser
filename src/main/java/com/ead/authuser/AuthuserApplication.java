package com.ead.authuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AuthuserApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthuserApplication.class, args);
    }

}
