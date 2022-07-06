package com.ead.authuser.framework.adapters.out.repositories;

import com.ead.authuser.application.port.out.RoleRepositoryPortOut;
import com.ead.authuser.domain.models.RoleModel;
import com.ead.authuser.domain.models.enums.RoleType;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class RoleRepositoryPortOutImpl implements RoleRepositoryPortOut {

    private final RoleRepository roleRepository;

    public RoleRepositoryPortOutImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<RoleModel> findByRoleName(RoleType roleType) {
        return roleRepository.findByRoleName(roleType);
    }
}
