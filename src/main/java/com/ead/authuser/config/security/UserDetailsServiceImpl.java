package com.ead.authuser.config.security;

import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado " + username));
        return UserDetailsImpl.build(userModel);
    }


    public UserDetails loadUserById(UUID userId) throws AuthenticationCredentialsNotFoundException {
        UserModel userModel = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado " + userId));
        return UserDetailsImpl.build(userModel);
    }
}
