package com.ead.authuser.application.services;

import com.ead.authuser.application.port.out.RoleRepositoryPortOut;
import com.ead.authuser.domain.models.enums.RoleType;
import com.ead.authuser.domain.models.RoleModel;
import com.ead.authuser.application.port.in.RoleServicePortIn;
import java.util.Optional;

public class RoleServicePortInImpl implements RoleServicePortIn {

    private final RoleRepositoryPortOut roleRepository;

    public RoleServicePortInImpl(RoleRepositoryPortOut roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public Optional<RoleModel> findByRoleName(RoleType roleType) {
        return roleRepository.findByRoleName(roleType);
    }
}
