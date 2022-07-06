package com.ead.authuser.framework.security;

import com.ead.authuser.domain.models.UserModel;
import com.ead.authuser.framework.adapters.out.repositories.UserRepository;
import com.ead.authuser.domain.exceptions.ResourceNotFoundException;
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
