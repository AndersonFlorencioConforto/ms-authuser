package com.ead.authuser.domain.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDTO {

    @NotBlank(message = "Não pode ser nulo e nem vazio")
    private String username;
    @NotBlank(message = "Não pode ser nulo e nem vazio")
    private String password;
}
