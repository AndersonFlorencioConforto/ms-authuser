package com.ead.authuser.models;

import com.ead.authuser.dtos.UserEventPublisherDTO;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // ignora os atributos com valores nulos, á nível de classe.
@Entity
@Table(name = "TB_USERS")
public class UserModel extends RepresentationModel<UserModel> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;

    @Column(nullable = false,unique = true,length = 50)
    private String username;

    @Column(nullable = false,unique = true,length = 50)
    private String email;

    @Column(nullable = false,length = 255)
    @JsonIgnore//não retorna o campo nas chamadas HTTPS
    private String password;

    @Column(nullable = false,length = 150)
    private String fullName;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 20)
    private String cpf;

    @Column
    private String imageUrl;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @CreationTimestamp
    private LocalDateTime creationDate;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @UpdateTimestamp
    private LocalDateTime lastUpdateDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus userStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Apenas passar no Json, não aparece na no retorno
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "TB_USERS_ROLES",
    joinColumns = @JoinColumn (name = "user_id"),
    inverseJoinColumns = @JoinColumn (name = "role_id"))
    private Set<RoleModel> roles = new HashSet<>();

    public UserEventPublisherDTO ConvertUserEventPublisherDTO() {
        UserEventPublisherDTO userEventPublisherDTO = new UserEventPublisherDTO();
        BeanUtils.copyProperties(this,userEventPublisherDTO);
        userEventPublisherDTO.setUserType(this.getUserType().toString());
        userEventPublisherDTO.setUserStatus(this.getUserStatus().toString());
        return userEventPublisherDTO;
    }
}