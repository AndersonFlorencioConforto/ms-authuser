package com.ead.authuser.domain.dtos;

import com.ead.authuser.framework.validations.UsernameConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    public interface UserView{
        public static interface RegistrationPost{}
        public static interface UserPut{}
        public static interface PasswordPut{}
        public static interface ImagePut{}
    }

    @JsonView(UserView.RegistrationPost.class)
    @NotBlank(groups = UserView.RegistrationPost.class)
    @Size(min = 4,max = 50,groups = UserView.RegistrationPost.class,message = "Tamanho min de 4 caracteres e max 50")
    @UsernameConstraint(groups = UserView.RegistrationPost.class)
    private String username;

    @JsonView(UserView.RegistrationPost.class)
    @NotBlank(groups = UserView.RegistrationPost.class)
    @Email(groups = UserView.RegistrationPost.class)
    @Size(min = 4,max = 50,groups = UserView.RegistrationPost.class,message = "Tamanho min de 4 caracteres e max 50")
    private String email;

    @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
    @NotBlank(groups = {UserView.RegistrationPost.class,UserView.PasswordPut.class})
    @Size(min = 6,max = 20,groups = {UserView.RegistrationPost.class,UserView.PasswordPut.class},message = "Tamanho min de 6 caracteres e max 20")
    private String password;

    @JsonView(UserView.PasswordPut.class)
    @NotBlank(groups = UserView.PasswordPut.class)
    @Size(min = 6,max = 20,groups = UserView.PasswordPut.class,message = "Tamanho min de 6 caracteres e max 20")
    private String oldPassword;

    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String fullName;

    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String phoneNumber;

    @JsonView(UserView.RegistrationPost.class)
    private String cpf;

    @JsonView(UserView.ImagePut.class)
    @NotBlank(groups = UserView.ImagePut.class)
    private String imageUrl;

}
